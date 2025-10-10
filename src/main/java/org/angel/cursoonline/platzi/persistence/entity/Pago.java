package org.angel.cursoonline.platzi.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
@Getter @Setter
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private Long idPago;

    @Column(name = "id_alumno", nullable = false)
    private Long idAlumno;

    // Dejarlo como Long y no forzar una relación si la relación Pago-Curso no es estricta
    @Column(name = "id_curso")
    private Long idCurso;

    @Column(name = "monto", nullable = false)
    private Double monto;

    @Column(name = "fecha_pago", nullable = false)
    private LocalDateTime fechaPago;

    @Column(name = "metodo_pago", length = 50, nullable = false)
    private String metodoPago;
}