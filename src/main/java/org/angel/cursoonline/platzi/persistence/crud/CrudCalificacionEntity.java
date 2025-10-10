package org.angel.cursoonline.platzi.persistence.crud;

import org.angel.cursoonline.platzi.persistence.entity.Calificacion;
import org.angel.cursoonline.platzi.persistence.entity.CalificacionPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrudCalificacionEntity extends JpaRepository<Calificacion, CalificacionPK> {

}