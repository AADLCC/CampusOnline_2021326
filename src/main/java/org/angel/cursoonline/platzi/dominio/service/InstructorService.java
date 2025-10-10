package org.angel.cursoonline.platzi.dominio.service;


import org.springframework.stereotype.Service;
import org.angel.cursoonline.platzi.dominio.dto.InstructorDTO;
import org.angel.cursoonline.platzi.dominio.dto.ModInstructorDTO;
import org.angel.cursoonline.platzi.dominio.repository.InstructorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class InstructorService {

    private final InstructorRepository instructorRepository;

    public InstructorService(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    public List<InstructorDTO> obtenerTodos(){
        return this.instructorRepository.obtenerTodos();
    }

    public Optional<InstructorDTO> buscarPorID(Long idInstructor){
        return this.instructorRepository.buscarPorID(idInstructor);
    }

    public InstructorDTO guardarInstructor(InstructorDTO instructorDTO){
        return this.instructorRepository.guardarInstructor(instructorDTO);
    }

    public Optional<InstructorDTO> modificarInstructor(Long idInstructor, ModInstructorDTO modInstructorDTO){
        return this.instructorRepository.modificarInstructor(idInstructor, modInstructorDTO);
    }

    public void eliminarInstructor(Long idInstructor){
        this.instructorRepository.eliminarInstructor(idInstructor);
    }
}