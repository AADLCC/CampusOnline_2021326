package org.angel.cursoonline.platzi.persistence.entity;

import lombok.Data;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Data
@Embeddable
public class InscripcionPK implements Serializable {

    @Column(name = "id_alumno")
    private Long idAlumno;

    @Column(name = "id_curso")
    private Long idCurso;
}