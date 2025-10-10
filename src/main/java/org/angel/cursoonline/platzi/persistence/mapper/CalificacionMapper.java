package org.angel.cursoonline.platzi.persistence.mapper;

import org.angel.cursoonline.platzi.dominio.dto.CalificacionDTO;
import org.angel.cursoonline.platzi.persistence.entity.Alumno;
import org.angel.cursoonline.platzi.persistence.entity.Calificacion;
import org.angel.cursoonline.platzi.persistence.entity.Curso;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

// *** LA SOLUCIÓN ESTÁ EN ESTA LÍNEA ***
// Añadimos el atributo "imports" para que MapStruct sepa qué clases usar en las expresiones.
@Mapper(componentModel = "spring", imports = {Alumno.class, Curso.class})
public interface CalificacionMapper {

    // --- DTO a Entidad ---
    @Mapping(target = "id.idAlumno", source = "idAlumno")
    @Mapping(target = "id.idCurso", source = "idCurso")
    // Estas expresiones ahora funcionarán porque MapStruct importará Alumno y Curso
    @Mapping(target = "alumno", expression = "java(new Alumno(dto.idAlumno()))")
    @Mapping(target = "curso", expression = "java(new Curso(dto.idCurso()))")
    Calificacion toCalificacion(CalificacionDTO dto);

    // --- Entidad a DTO ---
    @Mapping(source = "id.idAlumno", target = "idAlumno")
    @Mapping(source = "id.idCurso", target = "idCurso")
    CalificacionDTO toCalificacionDTO(Calificacion entity);

    List<CalificacionDTO> toCalificacionDTOs(List<Calificacion> entities);
}