package org.angel.cursoonline.platzi.persistence.mapper;

import org.angel.cursoonline.platzi.dominio.dto.AlumnoDTO;
import org.angel.cursoonline.platzi.persistence.entity.Alumno;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface AlumnoMapper {

    @Mapping(target = "idAlumno", source = "idAlumno")
    AlumnoDTO toDTO(Alumno alumno);
    List<AlumnoDTO> toDTOs(List<Alumno> alumnos);

    Alumno toEntity(AlumnoDTO alumnoDTO);
}