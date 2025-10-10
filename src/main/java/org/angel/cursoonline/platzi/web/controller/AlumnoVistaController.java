package org.angel.cursoonline.platzi.web.controller;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.angel.cursoonline.platzi.dominio.dto.AlumnoDTO;
import org.angel.cursoonline.platzi.dominio.service.AlumnoService;
import org.angel.cursoonline.platzi.persistence.entity.Alumno;
import org.angel.cursoonline.platzi.persistence.mapper.AlumnoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Named("alumnoVistaController")
@ViewScoped
@Getter
@Setter
public class AlumnoVistaController implements Serializable {

    @Autowired
    private AlumnoService alumnoService;

    @Autowired
    private AlumnoMapper alumnoMapper;

    private List<Alumno> alumnos;
    private Alumno alumnoSeleccionado;

    @PostConstruct
    public void init() {
        cargarAlumnos();
    }

    private void cargarAlumnos() {
        List<AlumnoDTO> dtos = alumnoService.obtenerTodos();
        this.alumnos = dtos.stream()
                .map(alumnoMapper::toEntity)
                .collect(Collectors.toList());

        this.alumnoSeleccionado = null;
    }

    public void prepararNuevoAlumno() {
        this.alumnoSeleccionado = new Alumno();
    }

    public void seleccionarAlumno(Alumno alumno) {
        this.alumnoSeleccionado = alumno;
    }

    public void guardarAlumno() {
        try {
            AlumnoDTO alumnoDTO = new AlumnoDTO(
                    this.alumnoSeleccionado.getIdAlumno(),
                    this.alumnoSeleccionado.getNombre(),
                    this.alumnoSeleccionado.getApellido(),
                    this.alumnoSeleccionado.getEmail()
            );

            if (this.alumnoSeleccionado.getIdAlumno() == null) {
                // Creación
                alumnoService.guardarAlumno(alumnoDTO);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Alumno creado correctamente."));
            } else {
                // Actualización
                alumnoService.guardarAlumno(alumnoDTO);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Alumno actualizado correctamente."));
            }

            cargarAlumnos();
            this.alumnoSeleccionado = null;

        } catch (DataIntegrityViolationException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El email ya está registrado. Verifique los datos."));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ocurrió un error al guardar el alumno."));
            e.printStackTrace();
        }
    }

    public void eliminarAlumno() {
        if (this.alumnoSeleccionado != null && this.alumnoSeleccionado.getIdAlumno() != null) {
            try {
                alumnoService.eliminarAlumno(this.alumnoSeleccionado.getIdAlumno());

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminado", "El alumno ha sido eliminado."));

                cargarAlumnos();
                this.alumnoSeleccionado = null;

            } catch (DataIntegrityViolationException e) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Acción Denegada", "No se puede eliminar el alumno porque está inscrito en uno o más cursos."));
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ocurrió un error inesperado al eliminar."));
            }
        }
    }
}