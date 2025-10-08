package org.angel.cursoonline.platzi.persistence.crud;

import org.angel.cursoonline.platzi.persistence.entity.Inscripcion;
import org.angel.cursoonline.platzi.persistence.entity.InscripcionPK;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CrudInscripcionEntity extends CrudRepository<Inscripcion, InscripcionPK> {

    List<Inscripcion> findByIdIdAlumno(Long idAlumno);
    List<Inscripcion> findByIdIdCurso(Long idCurso);
}