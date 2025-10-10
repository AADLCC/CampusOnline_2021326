package org.angel.cursoonline.platzi.dominio.service;

import org.springframework.stereotype.Service;
import org.angel.cursoonline.platzi.dominio.dto.CursoDTO;
import org.angel.cursoonline.platzi.dominio.dto.ModCursoDTO;
import org.angel.cursoonline.platzi.dominio.repository.CursoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public List<CursoDTO> obtenerTodos(){
        return this.cursoRepository.obtenerTodos();
    }

    public Optional<CursoDTO> buscarPorID(Long idCurso){
        return this.cursoRepository.buscarPorID(idCurso);
    }

    public CursoDTO guardarCurso(CursoDTO cursoDTO){
        return this.cursoRepository.guardarCurso(cursoDTO);
    }

    public Optional<CursoDTO> modificarCurso(Long idCurso, ModCursoDTO modCursoDTO){
        return this.cursoRepository.modificarCurso(idCurso, modCursoDTO);
    }

    public void eliminarCurso(Long idCurso){
        this.cursoRepository.eliminarCurso(idCurso);
    }
}