package org.angel.cursoonline.platzi.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Instructor")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInstructor;

    @Column(name = "nombreInstructor", nullable = false, length = 100)
    private String nombreInstructor;

    @Column(name = "apellidoInstructor", nullable = false, length = 100)
    private String apellidoInstructor;

    @Column(name = "especialidad", length = 100)
    private String especialidad;

    public Instructor(Long idInstructor) {
        this.idInstructor = idInstructor;
    }
}