package org.angel.cursoonline.platzi.web.controller;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.angel.cursoonline.platzi.dominio.dto.AlumnoDTO; // NUEVO
import org.angel.cursoonline.platzi.dominio.dto.CalificacionDTO;
import org.angel.cursoonline.platzi.dominio.dto.CursoDTO;   // NUEVO
import org.angel.cursoonline.platzi.dominio.service.AlumnoService; // NUEVO
import org.angel.cursoonline.platzi.dominio.service.CalificacionService;
import org.angel.cursoonline.platzi.dominio.service.CursoService;   // NUEVO

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Named
@ViewScoped
@Getter
@Setter
public class CalificacionController implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<CalificacionDTO> calificaciones;

    private Long idAlumnoForm;
    private Long idCursoForm;
    private Double puntuacionForm;
    private LocalDateTime fechaCalificacionOriginal;

    private List<AlumnoDTO> listaAlumnos;
    private List<CursoDTO> listaCursos;

    @Inject
    private CalificacionService calificacionService;
    @Inject
    private AlumnoService alumnoService;
    @Inject
    private CursoService cursoService;

    @PostConstruct
    public void init() {
        cargarCalificaciones();
        this.listaAlumnos = alumnoService.obtenerTodos();
        this.listaCursos = cursoService.obtenerTodos();
    }

    public void cargarCalificaciones() {
        calificaciones = calificacionService.obtenerTodos();
    }

    public void prepararNuevaCalificacion() {
        this.idAlumnoForm = null;
        this.idCursoForm = null;
        this.puntuacionForm = 0.0;
        this.fechaCalificacionOriginal = null;
    }

    public void seleccionarCalificacion(CalificacionDTO calificacion) {
        this.idAlumnoForm = calificacion.idAlumno();
        this.idCursoForm = calificacion.idCurso();
        this.puntuacionForm = calificacion.puntuacion();
        this.fechaCalificacionOriginal = calificacion.fechaCalificacion();
    }

    public void guardarCalificacion() {
        try {
            LocalDateTime fecha = (this.fechaCalificacionOriginal != null)
                    ? this.fechaCalificacionOriginal
                    : LocalDateTime.now();

            CalificacionDTO calificacionAGuardar = new CalificacionDTO(
                    this.idAlumnoForm,
                    this.idCursoForm,
                    this.puntuacionForm,
                    fecha
            );

            calificacionService.guardarCalificacion(calificacionAGuardar);
            cargarCalificaciones();

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Calificación guardada correctamente"));

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo guardar la calificación: " + e.getMessage()));
        }
    }

    public void eliminarCalificacion(Long idAlumno, Long idCurso) {
        try {
            calificacionService.eliminarCalificacion(idAlumno, idCurso);
            cargarCalificaciones();

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminado", "Calificación eliminada"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo eliminar la calificación"));
        }
    }
}