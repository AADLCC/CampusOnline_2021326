package org.angel.cursoonline.platzi.dominio.repository;

import org.angel.cursoonline.platzi.dominio.dto.CalificacionDTO;

import java.util.List;
import java.util.Optional;

public interface CalificacionRepository {

    List<CalificacionDTO> obtenerTodos();

    Optional<CalificacionDTO> buscarPorIDs(Long idAlumno, Long idCurso);

    CalificacionDTO guardarCalificacion(CalificacionDTO calificacionDTO);

    void eliminarCalificacion(Long idAlumno, Long idCurso);
}
