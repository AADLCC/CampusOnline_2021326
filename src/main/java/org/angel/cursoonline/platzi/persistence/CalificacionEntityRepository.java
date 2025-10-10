package org.angel.cursoonline.platzi.persistence;

import org.angel.cursoonline.platzi.dominio.dto.CalificacionDTO;
import org.angel.cursoonline.platzi.dominio.repository.CalificacionRepository;
import org.angel.cursoonline.platzi.persistence.crud.CrudCalificacionEntity;
import org.angel.cursoonline.platzi.persistence.entity.Calificacion;
import org.angel.cursoonline.platzi.persistence.entity.CalificacionPK;
import org.angel.cursoonline.platzi.persistence.mapper.CalificacionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class CalificacionEntityRepository implements CalificacionRepository {

    @Autowired
    private CrudCalificacionEntity crudCalificacionEntity;

    @Autowired
    private CalificacionMapper calificacionMapper;

    @Override
    public List<CalificacionDTO> obtenerTodos() {
        return StreamSupport.stream(crudCalificacionEntity.findAll().spliterator(), false)
                .map(calificacionMapper::toCalificacionDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CalificacionDTO> buscarPorIDs(Long idAlumno, Long idCurso) {
        CalificacionPK pk = new CalificacionPK(idAlumno, idCurso);
        return crudCalificacionEntity.findById(pk)
                .map(calificacionMapper::toCalificacionDTO);
    }

    @Override
    public CalificacionDTO guardarCalificacion(CalificacionDTO calificacionDTO) {
        Calificacion calificacion = calificacionMapper.toCalificacion(calificacionDTO);
        Calificacion guardada = crudCalificacionEntity.save(calificacion);
        return calificacionMapper.toCalificacionDTO(guardada);
    }

    @Override
    public void eliminarCalificacion(Long idAlumno, Long idCurso) {
        crudCalificacionEntity.deleteById(new CalificacionPK(idAlumno, idCurso));
    }
}
