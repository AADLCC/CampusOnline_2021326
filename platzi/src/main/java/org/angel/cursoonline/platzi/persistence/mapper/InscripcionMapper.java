package org.angel.cursoonline.platzi.persistence.mapper;

import org.angel.cursoonline.platzi.dominio.dto.InscripcionDTO;
import org.angel.cursoonline.platzi.persistence.entity.Inscripcion;
import org.angel.cursoonline.platzi.persistence.entity.InscripcionPK;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InscripcionMapper {

    @Mapping(target = "idAlumno", source = "id.idAlumno")
    @Mapping(target = "idCurso", source = "id.idCurso")
    InscripcionDTO toDTO(Inscripcion inscripcion);
    List<InscripcionDTO> toDTOs(List<Inscripcion> inscripciones);

    @Mapping(target = "id.idAlumno", source = "idAlumno")
    @Mapping(target = "id.idCurso", source = "idCurso")
    @Mapping(target = "alumno", ignore = true)
    @Mapping(target = "curso", ignore = true)
    Inscripcion toEntity(InscripcionDTO inscripcionDTO);
}