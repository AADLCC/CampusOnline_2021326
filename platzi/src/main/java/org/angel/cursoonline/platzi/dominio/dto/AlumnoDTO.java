package org.angel.cursoonline.platzi.dominio.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AlumnoDTO(
        Long idAlumno,

        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100, message = "Máximo 100 caracteres")
        String nombre,

        @NotBlank(message = "El apellido es obligatorio")
        @Size(max = 100, message = "Máximo 100 caracteres")
        String apellido,

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El formato del email no es válido")
        @Size(max = 150, message = "Máximo 150 caracteres")
        String email
) {
}