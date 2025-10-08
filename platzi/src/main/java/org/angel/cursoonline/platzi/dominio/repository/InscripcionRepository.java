package org.angel.cursoonline.platzi.dominio.repository;

import org.angel.cursoonline.platzi.dominio.dto.InscripcionDTO;
import org.angel.cursoonline.platzi.dominio.dto.ModInscripcionDTO;

import java.util.List;
import java.util.Optional;

public interface InscripcionRepository {
    List<InscripcionDTO> obtenerTodos();
    Optional<InscripcionDTO> buscarPorIDs(Long idAlumno, Long idCurso);
    List<InscripcionDTO> buscarPorAlumno(Long idAlumno);
    List<InscripcionDTO> buscarPorCurso(Long idCurso);
    InscripcionDTO guardarInscripcion(InscripcionDTO inscripcionDTO);
    Optional<InscripcionDTO> modificarInscripcion(Long idAlumno, Long idCurso, ModInscripcionDTO modInscripcionDTO);
    void eliminarInscripcion(Long idAlumno, Long idCurso);
}