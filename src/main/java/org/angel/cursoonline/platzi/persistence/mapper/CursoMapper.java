package org.angel.cursoonline.platzi.persistence.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.angel.cursoonline.platzi.dominio.dto.CursoDTO;
import org.angel.cursoonline.platzi.dominio.dto.ModCursoDTO;
import org.angel.cursoonline.platzi.persistence.entity.Curso;
import org.angel.cursoonline.platzi.persistence.entity.Instructor;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CursoMapper {

    @Mapping(source = "instructor.idInstructor", target = "idInstructor")
    CursoDTO toDto(Curso curso);

    List<CursoDTO> toDto(List<Curso> cursos);

    @InheritInverseConfiguration

    @Mapping(target = "instructor", expression = "java(new Instructor(cursoDTO.idInstructor()))")
    @Mapping(target = "idCurso", ignore = true)
    Curso toEntity(CursoDTO cursoDTO);


    @Mapping(target = "idCurso", ignore = true)
    @Mapping(target = "instructor", expression = "java(new Instructor(modCursoDTO.idInstructor()))")
    void updateEntityFromModDto(ModCursoDTO modCursoDTO, @MappingTarget Curso curso);
}
