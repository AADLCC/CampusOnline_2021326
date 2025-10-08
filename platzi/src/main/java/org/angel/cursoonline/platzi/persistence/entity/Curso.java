package org.angel.cursoonline.platzi.persistence.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "Curso")
@Data
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
}
