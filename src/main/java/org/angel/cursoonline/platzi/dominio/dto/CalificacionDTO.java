package org.angel.cursoonline.platzi.dominio.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record CalificacionDTO(
        @NotNull(message = "El ID del alumno es obligatorio")
        Long idAlumno,

        @NotNull(message = "El ID del curso es obligatorio")
        Long idCurso,

        @NotNull(message = "La puntuación es obligatoria")
        @DecimalMin(value = "0.0", message = "La puntuación mínima es 0.0")
        @DecimalMax(value = "100.0", message = "La puntuación máxima es 100.0")
        Double puntuacion,


        LocalDateTime fechaCalificacion
) {}