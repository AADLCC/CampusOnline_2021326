package org.angel.cursoonline.platzi.web.controller;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.angel.cursoonline.platzi.dominio.repository.InstructorRepository;
import org.angel.cursoonline.platzi.persistence.mapper.InstructorMapper;
import org.angel.cursoonline.platzi.persistence.crud.CrudInstructorEntity;
import org.angel.cursoonline.platzi.dominio.dto.InstructorDTO;
import org.angel.cursoonline.platzi.dominio.dto.ModInstructorDTO;
import org.angel.cursoonline.platzi.persistence.entity.Instructor;
import org.angel.cursoonline.platzi.dominio.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Named("instructorVistaController")
@ViewScoped
@Getter
@Setter
public class InstructorVistaController implements Serializable {

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private InstructorMapper instructorMapper;

    private List<Instructor> instructores;
    private Instructor instructorSeleccionado;

    @PostConstruct
    public void init() {
        cargarInstructores();
    }

    private void cargarInstructores() {
        List<InstructorDTO> dtos = instructorService.obtenerTodos();
        this.instructores = dtos.stream()
                .map(instructorMapper::toEntity)
                .collect(Collectors.toList());

        this.instructorSeleccionado = null;
    }

    public void prepararNuevoInstructor() {
        this.instructorSeleccionado = new Instructor();
    }

    public void seleccionarInstructor(Instructor instructor) {
        this.instructorSeleccionado = instructor;
    }

    public void guardarInstructor() {
        try {
            if (this.instructorSeleccionado.getIdInstructor() == null) {
                InstructorDTO nuevoDto = new InstructorDTO(
                        null,
                        this.instructorSeleccionado.getNombreInstructor(),
                        this.instructorSeleccionado.getApellidoInstructor(),
                        this.instructorSeleccionado.getEspecialidad()
                );
                instructorService.guardarInstructor(nuevoDto);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Instructor registrado."));

            } else {
                ModInstructorDTO modDto = new ModInstructorDTO(
                        this.instructorSeleccionado.getNombreInstructor(),
                        this.instructorSeleccionado.getApellidoInstructor(),
                        this.instructorSeleccionado.getEspecialidad()
                );
                instructorService.modificarInstructor(this.instructorSeleccionado.getIdInstructor(), modDto);

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Datos del instructor actualizados."));
            }

            cargarInstructores();
            this.instructorSeleccionado = null;

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ocurrió un error al guardar el instructor."));
        }
    }

    public void eliminarInstructor() {
        if (this.instructorSeleccionado != null && this.instructorSeleccionado.getIdInstructor() != null) {
            try {
                instructorService.eliminarInstructor(this.instructorSeleccionado.getIdInstructor());

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminado", "El instructor ha sido eliminado."));

                cargarInstructores();
                this.instructorSeleccionado = null;

            } catch (DataIntegrityViolationException e) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Acción Denegada", "No se puede eliminar el instructor porque tiene cursos asignados."));
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ocurrió un error inesperado al eliminar."));
            }
        }
    }
}