package org.angel.cursoonline.platzi.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import java.io.Serializable;

@Embeddable
@Getter @Setter
@EqualsAndHashCode // ¡Fundamental para claves compuestas!
@NoArgsConstructor
@AllArgsConstructor
public class CalificacionPK implements Serializable {

    @Column(name = "id_alumno")
    private Long idAlumno;

    @Column(name = "id_curso")
    private Long idCurso;
}
