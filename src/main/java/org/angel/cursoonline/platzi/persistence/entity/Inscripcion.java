package org.angel.cursoonline.platzi.persistence.entity;

import lombok.Data;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Inscripcion")
@NoArgsConstructor
public class Inscripcion {

    @EmbeddedId
    private InscripcionPK id = new InscripcionPK(); 
    @Column(name = "fecha_inscripcion", nullable = false)
    private LocalDateTime fechaInscripcion;

    @Column(name = "estado", nullable = false, length = 20)
    private String estado;

    @ManyToOne(fetch = FetchType.LAZY) 
    @MapsId("idAlumno")
    @JoinColumn(name = "id_alumno", nullable = false)
    private Alumno alumno;

    @ManyToOne(fetch = FetchType.LAZY) 
    @MapsId("idCurso")
    @JoinColumn(name = "id_curso", nullable = false)
    private Curso curso;

    public Inscripcion(Alumno alumno, Curso curso, LocalDateTime fecha, String estado) {
       
        this.alumno = alumno;
        this.curso = curso;
        this.fechaInscripcion = fecha;
        this.estado = estado;
      
    }
}