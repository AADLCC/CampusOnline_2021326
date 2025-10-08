package org.angel.cursoonline.platzi.persistence;

import org.angel.cursoonline.platzi.dominio.dto.InscripcionDTO;
import org.angel.cursoonline.platzi.dominio.dto.ModInscripcionDTO;
import org.angel.cursoonline.platzi.dominio.repository.InscripcionRepository;
import org.angel.cursoonline.platzi.persistence.crud.CrudInscripcionEntity;
import org.angel.cursoonline.platzi.persistence.crud.CrudAlumnoEntity; // <-- Nuevo Import
import org.angel.cursoonline.platzi.persistence.crud.CrudCursoEntity;   // <-- Nuevo Import
import org.angel.cursoonline.platzi.persistence.entity.Alumno;           // <-- Nuevo Import
import org.angel.cursoonline.platzi.persistence.entity.Curso;            // <-- Nuevo Import
import org.angel.cursoonline.platzi.persistence.entity.Inscripcion;
import org.angel.cursoonline.platzi.persistence.entity.InscripcionPK;
import org.angel.cursoonline.platzi.persistence.mapper.InscripcionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class InscripcionEntityRepository implements InscripcionRepository {

    @Autowired
    private CrudInscripcionEntity crudInscripcionEntity;

    @Autowired // <-- Inyectar CrudAlumnoEntity
    private CrudAlumnoEntity crudAlumnoEntity;

    @Autowired // <-- Inyectar CrudCursoEntity
    private CrudCursoEntity crudCursoEntity;

    @Autowired
    private InscripcionMapper inscripcionMapper;

    @Override
    public List<InscripcionDTO> obtenerTodos() {
        return StreamSupport.stream(crudInscripcionEntity.findAll().spliterator(), false)
                .map(inscripcionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<InscripcionDTO> buscarPorIDs(Long idAlumno, Long idCurso) {
        InscripcionPK pk = new InscripcionPK();
        pk.setIdAlumno(idAlumno);
        pk.setIdCurso(idCurso);

        return crudInscripcionEntity.findById(pk)
                .map(inscripcionMapper::toDTO);
    }

    @Override
    public List<InscripcionDTO> buscarPorAlumno(Long idAlumno) {
        return crudInscripcionEntity.findByIdIdAlumno(idAlumno).stream()
                .map(inscripcionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<InscripcionDTO> buscarPorCurso(Long idCurso) {
        return crudInscripcionEntity.findByIdIdCurso(idCurso).stream()
                .map(inscripcionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public InscripcionDTO guardarInscripcion(InscripcionDTO inscripcionDTO) {
        // 1. Mapear DTO a Entidad (Alumno y Curso serán null)
        Inscripcion inscripcion = inscripcionMapper.toEntity(inscripcionDTO);

        // 2. Cargar las entidades de referencia
        Optional<Alumno> alumnoOpt = crudAlumnoEntity.findById(inscripcionDTO.idAlumno());
        Optional<Curso> cursoOpt = crudCursoEntity.findById(inscripcionDTO.idCurso());

        if (alumnoOpt.isEmpty() || cursoOpt.isEmpty()) {
            // Lanza excepción si el ID no existe (aunque el controlador debería validarlo)
            throw new RuntimeException("Error al guardar: El Alumno o Curso con los IDs proporcionados no existen.");
        }

        // 3. Asignar las entidades cargadas a la Inscripcion
        inscripcion.setAlumno(alumnoOpt.get());
        inscripcion.setCurso(cursoOpt.get());

        // 4. Asegurar que la PK esté seteada (aunque ya se hizo en el mapper)
        InscripcionPK pk = new InscripcionPK();
        pk.setIdAlumno(inscripcionDTO.idAlumno());
        pk.setIdCurso(inscripcionDTO.idCurso());
        inscripcion.setId(pk);

        // 5. Guardar la entidad completa
        return inscripcionMapper.toDTO(crudInscripcionEntity.save(inscripcion));
    }

    @Override
    public Optional<InscripcionDTO> modificarInscripcion(Long idAlumno, Long idCurso, ModInscripcionDTO modInscripcionDTO) {
        InscripcionPK pk = new InscripcionPK();
        pk.setIdAlumno(idAlumno);
        pk.setIdCurso(idCurso);

        return crudInscripcionEntity.findById(pk).map(inscripcion -> {
            // Actualizar campos permitidos por el ModInscripcionDTO
            inscripcion.setEstado(modInscripcionDTO.estado());
            inscripcion.setFechaInscripcion(modInscripcionDTO.fechaInscripcion());

            return inscripcionMapper.toDTO(crudInscripcionEntity.save(inscripcion));
        });
    }

    @Override
    public void eliminarInscripcion(Long idAlumno, Long idCurso) {
        InscripcionPK pk = new InscripcionPK();
        pk.setIdAlumno(idAlumno);
        pk.setIdCurso(idCurso);

        crudInscripcionEntity.deleteById(pk);
    }
}