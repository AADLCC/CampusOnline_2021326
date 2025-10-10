package org.angel.cursoonline.platzi.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "calificaciones")
@Getter @Setter
public class Calificacion {

    @EmbeddedId
    private CalificacionPK id; 

    @Column(name = "puntuacion", nullable = false)
    private Double puntuacion;

    @Column(name = "fecha_calificacion", nullable = false)
    private LocalDateTime fechaCalificacion;


    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idAlumno")

    @JoinColumn(name = "id_alumno", nullable = false)
    private Alumno alumno;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idCurso")
    @JoinColumn(name = "id_curso", nullable = false)
    private Curso curso;
}