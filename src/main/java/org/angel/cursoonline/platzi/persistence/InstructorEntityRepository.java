package org.angel.cursoonline.platzi.persistence;

import org.springframework.stereotype.Repository;
import org.angel.cursoonline.platzi.dominio.repository.InstructorRepository;
import org.angel.cursoonline.platzi.persistence.mapper.InstructorMapper;
import org.angel.cursoonline.platzi.persistence.crud.CrudInstructorEntity;
import org.angel.cursoonline.platzi.dominio.dto.InstructorDTO;
import org.angel.cursoonline.platzi.dominio.dto.ModInstructorDTO;
import org.angel.cursoonline.platzi.persistence.entity.Instructor;

import java.util.List;
import java.util.Optional;

@Repository
public class InstructorEntityRepository implements InstructorRepository {

    private final CrudInstructorEntity crudInstructorEntity;
    private final InstructorMapper instructorMapper;

    public InstructorEntityRepository(CrudInstructorEntity crudInstructorEntity, InstructorMapper instructorMapper) {
        this.crudInstructorEntity = crudInstructorEntity;
        this.instructorMapper = instructorMapper;
    }

    @Override
    public List<InstructorDTO> obtenerTodos() {
        return instructorMapper.toDto((List<Instructor>) crudInstructorEntity.findAll());
    }

    @Override
    public Optional<InstructorDTO> buscarPorID(Long idInstructor) {
        return crudInstructorEntity.findById(idInstructor).map(instructorMapper::toDto);
    }

    @Override
    public InstructorDTO guardarInstructor(InstructorDTO instructorDTO) {
        Instructor instructor = instructorMapper.toEntity(instructorDTO);
        return instructorMapper.toDto(crudInstructorEntity.save(instructor));
    }

    @Override
    public void eliminarInstructor(Long idInstructor) {
        crudInstructorEntity.deleteById(idInstructor);
    }

    @Override
    public Optional<InstructorDTO> modificarInstructor(Long idInstructor, ModInstructorDTO modInstructorDTO) {
        return crudInstructorEntity.findById(idInstructor)
                .map(instructorExistente -> {
                    instructorMapper.updateEntityFromModDto(modInstructorDTO, instructorExistente);
                    return instructorMapper.toDto(crudInstructorEntity.save(instructorExistente));
                });
    }
}