package org.angel.cursoonline.platzi.web.controller;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.angel.cursoonline.platzi.dominio.dto.CursoDTO;
import org.angel.cursoonline.platzi.dominio.dto.InstructorDTO;
import org.angel.cursoonline.platzi.dominio.dto.ModCursoDTO;
import org.angel.cursoonline.platzi.dominio.service.CursoService;
import org.angel.cursoonline.platzi.dominio.service.InstructorService;
import org.angel.cursoonline.platzi.persistence.entity.Curso;
import org.angel.cursoonline.platzi.persistence.entity.Instructor;
import org.angel.cursoonline.platzi.persistence.mapper.CursoMapper;
import org.angel.cursoonline.platzi.persistence.mapper.InstructorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Named("cursoVistaController")
@ViewScoped
@Getter
@Setter
public class CursoVistaController implements Serializable {

    @Autowired
    private CursoService cursoService;

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private CursoMapper cursoMapper;

    @Autowired
    private InstructorMapper instructorMapper;

    private List<Curso> cursos;
    private Curso cursoSeleccionado;
    private List<Instructor> instructoresDisponibles;

    private Long idInstructorSeleccionado;

    @PostConstruct
    public void init() {
        cargarCursos();
    }

    private void cargarListasSoporte() {
        List<InstructorDTO> dtos = instructorService.obtenerTodos();

        this.instructoresDisponibles = dtos.stream()
                .map(dto -> new Instructor(dto.idInstructor(), dto.nombreInstructor(), dto.apellidoInstructor(), dto.especialidad()))
                .collect(Collectors.toList());
    }


    private void cargarCursos() {
        List<CursoDTO> dtos = cursoService.obtenerTodos();
        this.cursos = dtos.stream()
                .map(cursoMapper::toEntity)
                .collect(Collectors.toList());

        this.cursoSeleccionado = null;
    }

    public void prepararNuevoCurso() {
        cargarListasSoporte();
        this.cursoSeleccionado = new Curso();
        this.cursoSeleccionado.setPrecio(BigDecimal.ZERO);
        this.cursoSeleccionado.setDuracionHoras(0);
        this.idInstructorSeleccionado = null;
    }

    // Se asegura de que la lista de instructores esté disponible antes de abrir el modal de edición.
    public void seleccionarCurso(Curso curso) {
        cargarListasSoporte();
        this.cursoSeleccionado = curso;
        this.idInstructorSeleccionado = curso.getInstructor() != null ? curso.getInstructor().getIdInstructor() : null;
    }
    // ----------------------------------------------------------------------

    public void guardarCurso() {
        try {
            if (this.idInstructorSeleccionado == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Debe seleccionar un Instructor."));
                return;
            }

            if (this.cursoSeleccionado.getIdCurso() == null) {
                // Creación
                CursoDTO nuevoDto = new CursoDTO(
                        null,
                        this.idInstructorSeleccionado,
                        this.cursoSeleccionado.getNombreCurso(),
                        this.cursoSeleccionado.getDescripcion(),
                        this.cursoSeleccionado.getDuracionHoras(),
                        this.cursoSeleccionado.getPrecio()
                );
                cursoService.guardarCurso(nuevoDto);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Curso creado correctamente."));

            } else {
                // Actualización
                ModCursoDTO modDto = new ModCursoDTO(
                        this.cursoSeleccionado.getNombreCurso(),
                        this.cursoSeleccionado.getDescripcion(),
                        this.cursoSeleccionado.getDuracionHoras(),
                        this.cursoSeleccionado.getPrecio(),
                        this.idInstructorSeleccionado
                );
                cursoService.modificarCurso(this.cursoSeleccionado.getIdCurso(), modDto);

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Curso actualizado correctamente."));
            }

            cargarCursos();
            this.cursoSeleccionado = null;

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ocurrió un error al guardar el curso."));
            e.printStackTrace();
        }
    }

    public void eliminarCurso() {
        if (this.cursoSeleccionado != null && this.cursoSeleccionado.getIdCurso() != null) {
            try {
                cursoService.eliminarCurso(this.cursoSeleccionado.getIdCurso());

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminado", "El curso ha sido eliminado."));

                cargarCursos();
                this.cursoSeleccionado = null;

            } catch (DataIntegrityViolationException e) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Acción Denegada", "No se puede eliminar el curso porque tiene inscripciones activas."));
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ocurrió un error inesperado al eliminar."));
            }
        }
    }
}