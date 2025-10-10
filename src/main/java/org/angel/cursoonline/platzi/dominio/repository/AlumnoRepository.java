package org.angel.cursoonline.platzi.dominio.repository;

import org.angel.cursoonline.platzi.dominio.dto.AlumnoDTO;
import java.util.List;
import java.util.Optional;

public interface AlumnoRepository {
    List<AlumnoDTO> obtenerTodos();
    Optional<AlumnoDTO> buscarPorID(Long idAlumno);
    AlumnoDTO guardarAlumno(AlumnoDTO alumnoDTO);
    void eliminarAlumno(Long idAlumno);
}