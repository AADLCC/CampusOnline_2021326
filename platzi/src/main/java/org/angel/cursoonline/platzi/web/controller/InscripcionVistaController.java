package org.angel.cursoonline.platzi.web.controller;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.angel.cursoonline.platzi.dominio.dto.InscripcionDTO;
import org.angel.cursoonline.platzi.dominio.dto.ModInscripcionDTO;
import org.angel.cursoonline.platzi.dominio.service.AlumnoService;
import org.angel.cursoonline.platzi.dominio.service.CursoService;
import org.angel.cursoonline.platzi.dominio.service.InscripcionService;
import org.angel.cursoonline.platzi.persistence.entity.Alumno;
import org.angel.cursoonline.platzi.persistence.entity.Curso;
import org.angel.cursoonline.platzi.persistence.entity.Inscripcion;
import org.angel.cursoonline.platzi.persistence.mapper.AlumnoMapper;
import org.angel.cursoonline.platzi.persistence.mapper.CursoMapper;
import org.angel.cursoonline.platzi.persistence.mapper.InscripcionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Named("inscripcionVistaController")
@ViewScoped
@Getter
@Setter
public class InscripcionVistaController implements Serializable {

    @Autowired
    private InscripcionService inscripcionService;

    @Autowired
    private AlumnoService alumnoService;

    @Autowired
    private CursoService cursoService;

    @Autowired
    private InscripcionMapper inscripcionMapper;

    @Autowired
    private AlumnoMapper alumnoMapper;

    @Autowired
    private CursoMapper cursoMapper;

    // Listas de datos
    private List<Inscripcion> inscripciones;
    private List<Alumno> alumnosDisponibles;
    private List<Curso> cursosDisponibles;

    // Campos de formulario
    private Inscripcion inscripcionSeleccionada;
    private Alumno alumnoSeleccionado;
    private Curso cursoSeleccionado;
    private String estadoSeleccionado;
    private LocalDateTime fechaSeleccionada;

    // Opciones de estado
    private final List<String> estados = List.of("ACTIVA", "COMPLETADA", "CANCELADA");

    @PostConstruct
    public void init() {
        cargarInscripciones();
        cargarListasSoporte();
    }

    private void cargarListasSoporte() {
        this.alumnosDisponibles = alumnoService.obtenerTodos().stream()
                .map(alumnoMapper::toEntity)
                .collect(Collectors.toList());

        this.cursosDisponibles = cursoService.obtenerTodos().stream()
                .map(cursoMapper::toEntity)
                .collect(Collectors.toList());
    }

    private void cargarInscripciones() {
        this.inscripciones = inscripcionService.obtenerTodos().stream()
                .map(inscripcionMapper::toEntity)
                .collect(Collectors.toList());

        this.inscripciones.forEach(i -> {
            // *** CORRECCIÓN APLICADA AQUÍ: Se cambió obtenerPorId() por buscarPorID() ***
            alumnoService.buscarPorID(i.getId().getIdAlumno())
                    .map(alumnoMapper::toEntity)
                    .ifPresent(i::setAlumno);

            cursoService.buscarPorID(i.getId().getIdCurso())
                    .map(cursoMapper::toEntity)
                    .ifPresent(i::setCurso);
        });

        this.inscripcionSeleccionada = null;
    }

    public void prepararNuevaInscripcion() {
        cargarListasSoporte();
        this.inscripcionSeleccionada = new Inscripcion();
        this.alumnoSeleccionado = null;
        this.cursoSeleccionado = null;
        this.estadoSeleccionado = "ACTIVA";
        this.fechaSeleccionada = LocalDateTime.now();
    }

    public void seleccionarInscripcion(Inscripcion inscripcion) {
        cargarListasSoporte();
        this.inscripcionSeleccionada = inscripcion;
        this.alumnoSeleccionado = inscripcion.getAlumno();
        this.cursoSeleccionado = inscripcion.getCurso();
        this.estadoSeleccionado = inscripcion.getEstado();
        this.fechaSeleccionada = inscripcion.getFechaInscripcion();
    }

    public void guardarInscripcion() {
        try {
            if (alumnoSeleccionado == null || cursoSeleccionado == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Debe seleccionar un Alumno y un Curso."));
                return;
            }

            if (inscripcionSeleccionada.getId() == null) {
                InscripcionDTO nuevoDto = new InscripcionDTO(
                        alumnoSeleccionado.getIdAlumno(),
                        cursoSeleccionado.getIdCurso(),
                        fechaSeleccionada,
                        estadoSeleccionado
                );
                inscripcionService.guardarInscripcion(nuevoDto);

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Inscripción creada correctamente."));
            } else {
                ModInscripcionDTO modDto = new ModInscripcionDTO(
                        fechaSeleccionada,
                        estadoSeleccionado
                );

                inscripcionService.modificarInscripcion(
                        alumnoSeleccionado.getIdAlumno(),
                        cursoSeleccionado.getIdCurso(),
                        modDto
                );

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Inscripción actualizada correctamente."));
            }

            cargarInscripciones();
            inscripcionSeleccionada = null;

        } catch (DataIntegrityViolationException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El alumno ya está inscrito en este curso."));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ocurrió un error al guardar la inscripción."));
            e.printStackTrace();
        }
    }

    public void eliminarInscripcion() {
        if (inscripcionSeleccionada != null && inscripcionSeleccionada.getId() != null) {
            try {
                inscripcionService.eliminarInscripcion(
                        inscripcionSeleccionada.getAlumno().getIdAlumno(),
                        inscripcionSeleccionada.getCurso().getIdCurso()
                );

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminado", "La inscripción ha sido eliminada."));

                cargarInscripciones();
                inscripcionSeleccionada = null;

            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ocurrió un error inesperado al eliminar."));
            }
        }
    }
}