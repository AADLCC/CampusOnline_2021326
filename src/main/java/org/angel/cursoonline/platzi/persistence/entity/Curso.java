package org.angel.cursoonline.platzi.persistence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "Curso")
@NoArgsConstructor
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCurso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idInstructor", nullable = false)
    private Instructor instructor;

    @Column(name = "nombreCurso", nullable = false, length = 100)
    private String nombreCurso;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @Column(name = "duracionHoras", nullable = false)
    private Integer duracionHoras;

    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    public Curso(Long idCurso) {
        this.idCurso = idCurso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Curso)) return false;
        Curso curso = (Curso) o;
        return idCurso != null && idCurso.equals(curso.idCurso);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
