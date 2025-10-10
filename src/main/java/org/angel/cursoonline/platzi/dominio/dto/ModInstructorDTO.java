package org.angel.cursoonline.platzi.dominio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ModInstructorDTO(

        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100, message = "Máximo 100 caracteres")
        String nombreInstructor,

        @NotBlank(message = "El apellido es obligatorio")
        @Size(max = 100, message = "Máximo 100 caracteres")
        String apellidoInstructor,

        @NotBlank(message = "La especialidad es obligatoria")
        @Size(max = 100, message = "Máximo 100 caracteres")
        String especialidad
) {
}