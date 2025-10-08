package org.angel.cursoonline.platzi.persistence.crud;

import org.springframework.data.repository.CrudRepository;
import org.angel.cursoonline.platzi.persistence.entity.Curso;

public interface CrudCursoEntity extends CrudRepository<Curso, Long> {
}
