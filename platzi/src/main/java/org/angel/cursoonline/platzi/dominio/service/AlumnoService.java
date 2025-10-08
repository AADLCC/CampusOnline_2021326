package org.angel.cursoonline.platzi.dominio.service;

import org.springframework.stereotype.Service;
import org.angel.cursoonline.platzi.dominio.dto.AlumnoDTO;
import org.angel.cursoonline.platzi.dominio.repository.AlumnoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AlumnoService {

    private final AlumnoRepository alumnoRepository;

    public AlumnoService(AlumnoRepository alumnoRepository) {
        this.alumnoRepository = alumnoRepository;
    }

    public List<AlumnoDTO> obtenerTodos(){
        return this.alumnoRepository.obtenerTodos();
    }

    public Optional<AlumnoDTO> buscarPorID(Long idAlumno){
        return this.alumnoRepository.buscarPorID(idAlumno);
    }

    public AlumnoDTO guardarAlumno(AlumnoDTO alumnoDTO){
        return this.alumnoRepository.guardarAlumno(alumnoDTO);
    }

    public void eliminarAlumno(Long idAlumno){
        this.alumnoRepository.eliminarAlumno(idAlumno);
    }
}