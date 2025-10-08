package org.angel.cursoonline.platzi.dominio.repository;

import org.angel.cursoonline.platzi.dominio.dto.InstructorDTO;
import org.angel.cursoonline.platzi.dominio.dto.ModInstructorDTO;
import java.util.List;
import java.util.Optional;

public interface InstructorRepository {
    List<InstructorDTO> obtenerTodos();
    Optional<InstructorDTO> buscarPorID(Long idInstructor);
    InstructorDTO guardarInstructor(InstructorDTO instructorDTO);
    void eliminarInstructor(Long idInstructor);
    Optional<InstructorDTO> modificarInstructor(Long idInstructor, ModInstructorDTO modInstructorDTO);
}