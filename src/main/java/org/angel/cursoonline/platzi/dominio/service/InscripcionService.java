package org.angel.cursoonline.platzi.dominio.service;

import org.springframework.stereotype.Service;
import org.angel.cursoonline.platzi.dominio.dto.InscripcionDTO;
import org.angel.cursoonline.platzi.dominio.dto.ModInscripcionDTO;
import org.angel.cursoonline.platzi.dominio.repository.InscripcionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class InscripcionService {

    private final InscripcionRepository inscripcionRepository;

    public InscripcionService(InscripcionRepository inscripcionRepository) {
        this.inscripcionRepository = inscripcionRepository;
    }

    public List<InscripcionDTO> obtenerTodos(){
        return this.inscripcionRepository.obtenerTodos();
    }

    public Optional<InscripcionDTO> buscarPorIDs(Long idAlumno, Long idCurso){
        return this.inscripcionRepository.buscarPorIDs(idAlumno, idCurso);
    }

    public List<InscripcionDTO> buscarPorAlumno(Long idAlumno){
        return this.inscripcionRepository.buscarPorAlumno(idAlumno);
    }

    public List<InscripcionDTO> buscarPorCurso(Long idCurso){
        return this.inscripcionRepository.buscarPorCurso(idCurso);
    }

    public InscripcionDTO guardarInscripcion(InscripcionDTO inscripcionDTO){
        return this.inscripcionRepository.guardarInscripcion(inscripcionDTO);
    }

    public Optional<InscripcionDTO> modificarInscripcion(Long idAlumno, Long idCurso, ModInscripcionDTO modInscripcionDTO){
        return this.inscripcionRepository.modificarInscripcion(idAlumno, idCurso, modInscripcionDTO);
    }

    public void eliminarInscripcion(Long idAlumno, Long idCurso){
        this.inscripcionRepository.eliminarInscripcion(idAlumno, idCurso);
    }
}