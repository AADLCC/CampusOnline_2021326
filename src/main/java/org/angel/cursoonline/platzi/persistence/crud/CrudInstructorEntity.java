package org.angel.cursoonline.platzi.persistence.crud;

import org.springframework.data.repository.CrudRepository;
import org.angel.cursoonline.platzi.persistence.entity.Instructor;

public interface CrudInstructorEntity extends CrudRepository<Instructor, Long> {
}
