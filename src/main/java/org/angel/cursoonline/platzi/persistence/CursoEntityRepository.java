package org.angel.cursoonline.platzi.persistence;

import org.springframework.stereotype.Repository;
import org.angel.cursoonline.platzi.dominio.repository.CursoRepository;
import org.angel.cursoonline.platzi.dominio.dto.CursoDTO;
import org.angel.cursoonline.platzi.dominio.dto.ModCursoDTO;
import org.angel.cursoonline.platzi.persistence.entity.Curso;
import org.angel.cursoonline.platzi.persistence.mapper.CursoMapper;
import org.angel.cursoonline.platzi.persistence.crud.CrudCursoEntity;

import java.util.List;
import java.util.Optional;

@Repository
public class CursoEntityRepository implements CursoRepository {

    private final CrudCursoEntity crudCursoEntity;
    private final CursoMapper cursoMapper;

    public CursoEntityRepository(CrudCursoEntity crudCursoEntity, CursoMapper cursoMapper) {
        this.crudCursoEntity = crudCursoEntity;
        this.cursoMapper = cursoMapper;
    }

    @Override
    public List<CursoDTO> obtenerTodos() {
        return cursoMapper.toDto((List<Curso>) crudCursoEntity.findAll());
    }

    @Override
    public Optional<CursoDTO> buscarPorID(Long idCurso) {
        return crudCursoEntity.findById(idCurso).map(cursoMapper::toDto);
    }

    @Override
    public CursoDTO guardarCurso(CursoDTO cursoDTO) {
        Curso curso = cursoMapper.toEntity(cursoDTO);
        return cursoMapper.toDto(crudCursoEntity.save(curso));
    }

    @Override
    public void eliminarCurso(Long idCurso) {
        crudCursoEntity.deleteById(idCurso);
    }

    @Override
    public Optional<CursoDTO> modificarCurso(Long idCurso, ModCursoDTO modCursoDTO) {
        return crudCursoEntity.findById(idCurso)
                .map(cursoExistente -> {
                    cursoMapper.updateEntityFromModDto(modCursoDTO, cursoExistente);
                    return cursoMapper.toDto(crudCursoEntity.save(cursoExistente));
                });
    }
}
