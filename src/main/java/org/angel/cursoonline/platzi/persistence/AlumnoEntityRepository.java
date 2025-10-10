package org.angel.cursoonline.platzi.persistence;

import org.angel.cursoonline.platzi.dominio.dto.AlumnoDTO;
import org.angel.cursoonline.platzi.dominio.repository.AlumnoRepository;
import org.angel.cursoonline.platzi.persistence.crud.CrudAlumnoEntity;
import org.angel.cursoonline.platzi.persistence.mapper.AlumnoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class AlumnoEntityRepository implements AlumnoRepository {

    @Autowired
    private CrudAlumnoEntity crudAlumnoEntity;

    @Autowired
    private AlumnoMapper alumnoMapper;

    @Override
    public List<AlumnoDTO> obtenerTodos() {
        return StreamSupport.stream(crudAlumnoEntity.findAll().spliterator(), false)
                .map(alumnoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AlumnoDTO> buscarPorID(Long idAlumno) {
        return crudAlumnoEntity.findById(idAlumno)
                .map(alumnoMapper::toDTO);
    }

    @Override
    public AlumnoDTO guardarAlumno(AlumnoDTO alumnoDTO) {
        // Al guardar, convertimos el DTO a Entity
        var alumno = alumnoMapper.toEntity(alumnoDTO);

        // La entidad guardada se convierte de nuevo a DTO
        return alumnoMapper.toDTO(crudAlumnoEntity.save(alumno));
    }

    @Override
    public void eliminarAlumno(Long idAlumno) {
        crudAlumnoEntity.deleteById(idAlumno);
    }
}