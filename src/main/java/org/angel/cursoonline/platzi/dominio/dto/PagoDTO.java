package org.angel.cursoonline.platzi.dominio.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public record PagoDTO(
        Long idPago, // Nullable para creaciones (INSERT)

        @NotNull(message = "El ID del alumno es obligatorio")
        Long idAlumno,

        Long idCurso, // Se mantiene como Long, puede ser nulo

        @NotNull(message = "El monto es obligatorio")
        @DecimalMin(value = "0.01", message = "El monto debe ser positivo (mínimo 0.01)")
        Double monto,

        @NotNull(message = "La fecha de pago es obligatoria")
        LocalDateTime fechaPago,

        @NotBlank(message = "El método de pago es obligatorio")
        @Size(max = 50, message = "Máximo 50 caracteres para el método de pago")
        String metodoPago
) {}