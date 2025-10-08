package org.angel.cursoonline.platzi.dominio.repository;

import org.angel.cursoonline.platzi.dominio.dto.ModCursoDTO;
import org.angel.cursoonline.platzi.dominio.dto.CursoDTO;
import java.util.List;
import java.util.Optional;

public interface CursoRepository {
    List<CursoDTO> obtenerTodos();
    Optional<CursoDTO> buscarPorID(Long idCurso);
    CursoDTO guardarCurso(CursoDTO cursoDTO);
    void eliminarCurso(Long idCurso);
    Optional<CursoDTO> modificarCurso(Long idCurso, ModCursoDTO modCursoDTO);
}