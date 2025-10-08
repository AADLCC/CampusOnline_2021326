    package org.angel.cursoonline.platzi.dominio.dto;

    import jakarta.validation.constraints.*;
    import java.math.BigDecimal;

    public record CursoDTO(
            Long idCurso,

            @NotNull(message = "El ID del instructor es obligatorio")
            Long idInstructor,

            @NotBlank(message = "El nombre del curso es obligatorio")
            @Size(max = 100, message = "Máximo 100 caracteres")
            String nombreCurso,

            @Size(max = 255, message = "Máximo 255 caracteres")
            String descripcion,

            @NotNull(message = "La duración es obligatoria")
            @Min(value = 1, message = "La duración debe ser al menos 1 hora")
            Integer duracionHoras,

            @NotNull(message = "El precio es obligatorio")
            @DecimalMin(value = "0.00", message = "El precio no puede ser negativo")
            BigDecimal precio
    ) {
    }