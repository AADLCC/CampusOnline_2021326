package org.angel.cursoonline.platzi.persistence.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.angel.cursoonline.platzi.dominio.dto.InstructorDTO;
import org.angel.cursoonline.platzi.dominio.dto.ModInstructorDTO;
import org.angel.cursoonline.platzi.persistence.entity.Instructor;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InstructorMapper {

    InstructorDTO toDto(Instructor instructor);

    List<InstructorDTO> toDto(List<Instructor> instructores);

    @InheritInverseConfiguration
    @Mapping(target = "idInstructor", ignore = true)
    Instructor toEntity(InstructorDTO instructorDTO);

    @Mapping(target = "idInstructor", ignore = true)
    void updateEntityFromModDto(ModInstructorDTO modInstructorDTO, @MappingTarget Instructor instructor);
}
