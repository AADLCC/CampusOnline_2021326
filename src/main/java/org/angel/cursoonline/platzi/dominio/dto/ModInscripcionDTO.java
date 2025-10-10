package org.angel.cursoonline.platzi.dominio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public record ModInscripcionDTO(

        @NotNull(message = "La fecha de inscripción es obligatoria")
        LocalDateTime fechaInscripcion,

        @NotBlank(message = "El estado es obligatorio")
        @Size(max = 20, message = "Máximo 20 caracteres")
        String estado
) {
}