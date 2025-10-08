package org.angel.cursoonline.platzi.persistence.entity;

import lombok.Data;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "incripcion")
@NoArgsConstructor
public class Inscripcion {

    // Clave primaria compuesta
    @EmbeddedId
    private InscripcionPK id;

    @Column(name = "fecha_inscripcion", nullable = false)
    private LocalDateTime fechaInscripcion;

    @Column(name = "estado", nullable = false, length = 20)
    private String estado;

    @ManyToOne
    @MapsId("idAlumno")
    @JoinColumn(name = "id_alumno", insertable = false, updatable = false)
    private Alumno alumno;

    @ManyToOne
    @MapsId("idCurso")
    @JoinColumn(name = "id_curso", insertable = false, updatable = false)
    private Curso curso;
}