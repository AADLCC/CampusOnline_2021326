package org.angel.cursoonline.platzi.persistence.crud;

import org.angel.cursoonline.platzi.persistence.entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrudPagoEntity extends JpaRepository<Pago, Long> {}
