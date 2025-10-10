package org.angel.cursoonline.platzi.persistence.entity;

import jakarta.persistence.Column; 
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Embeddable
public class InscripcionPK implements Serializable {

    @Column(name = "id_alumno") 
    private Long idAlumno;

    @Column(name = "id_curso") 
    private Long idCurso;

    public InscripcionPK(Long idAlumno, Long idCurso) {
        this.idAlumno = idAlumno;
        this.idCurso = idCurso;
    }

   
}