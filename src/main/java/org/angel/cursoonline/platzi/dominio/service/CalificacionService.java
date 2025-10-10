package org.angel.cursoonline.platzi.dominio.service;


import org.angel.cursoonline.platzi.dominio.dto.CalificacionDTO;
import org.angel.cursoonline.platzi.persistence.entity.Calificacion;
import org.angel.cursoonline.platzi.persistence.entity.CalificacionPK;
import org.angel.cursoonline.platzi.persistence.mapper.CalificacionMapper; 
import org.angel.cursoonline.platzi.dominio.repository.CalificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CalificacionService {

    @Autowired
    private CalificacionRepository calificacionRepository;

    
    public List<CalificacionDTO> obtenerTodos() {
        return calificacionRepository.obtenerTodos();
    }

    public Optional<CalificacionDTO> guardarCalificacion(CalificacionDTO dto) {
        return Optional.of(calificacionRepository.guardarCalificacion(dto));
    }

    public boolean eliminarCalificacion(Long idAlumno, Long idCurso) {
        calificacionRepository.eliminarCalificacion(idAlumno, idCurso);
        return true;
    }
}
