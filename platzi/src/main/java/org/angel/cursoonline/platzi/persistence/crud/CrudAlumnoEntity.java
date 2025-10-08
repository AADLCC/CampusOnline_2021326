package org.angel.cursoonline.platzi.persistence.crud;

import org.angel.cursoonline.platzi.persistence.entity.Alumno;
import org.springframework.data.repository.CrudRepository;

public interface CrudAlumnoEntity extends CrudRepository<Alumno, Long> {
}