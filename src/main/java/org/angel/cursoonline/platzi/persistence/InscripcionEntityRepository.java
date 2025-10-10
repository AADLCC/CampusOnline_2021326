package org.angel.cursoonline.platzi.persistence;

import org.angel.cursoonline.platzi.dominio.dto.InscripcionDTO;
import org.angel.cursoonline.platzi.dominio.dto.ModInscripcionDTO;
import org.angel.cursoonline.platzi.dominio.repository.InscripcionRepository;
import org.angel.cursoonline.platzi.persistence.crud.CrudInscripcionEntity;
import org.angel.cursoonline.platzi.persistence.crud.CrudAlumnoEntity;
import org.angel.cursoonline.platzi.persistence.crud.CrudCursoEntity;   
import org.angel.cursoonline.platzi.persistence.entity.Alumno;          
import org.angel.cursoonline.platzi.persistence.entity.Curso;            
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

    @Autowired
    private CrudAlumnoEntity crudAlumnoEntity;

    @Autowired
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

        Inscripcion inscripcion = inscripcionMapper.toEntity(inscripcionDTO);

        return inscripcionMapper.toDTO(crudInscripcionEntity.save(inscripcion));
    }

    @Override
    public Optional<InscripcionDTO> modificarInscripcion(Long idAlumno, Long idCurso, ModInscripcionDTO modInscripcionDTO) {
        InscripcionPK pk = new InscripcionPK();
        pk.setIdAlumno(idAlumno);
        pk.setIdCurso(idCurso);

        return crudInscripcionEntity.findById(pk).map(inscripcion -> {
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