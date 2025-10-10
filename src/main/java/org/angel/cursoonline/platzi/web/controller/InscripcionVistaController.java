package org.angel.cursoonline.platzi.web.controller;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.angel.cursoonline.platzi.dominio.dto.AlumnoDTO;
import org.angel.cursoonline.platzi.dominio.dto.CursoDTO;
import org.angel.cursoonline.platzi.dominio.dto.InscripcionDTO;

import org.angel.cursoonline.platzi.dominio.dto.ModInscripcionDTO;
import org.angel.cursoonline.platzi.dominio.service.AlumnoService;
import org.angel.cursoonline.platzi.dominio.service.CursoService;
import org.angel.cursoonline.platzi.dominio.service.InscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter; 
import java.util.List;
import java.util.Optional;

@Named("inscripcionVistaController")
@ViewScoped
@Component
@Getter
@Setter
public class InscripcionVistaController implements Serializable {

    // --- 1. INYECCIONES ---
    @Autowired
    private InscripcionService inscripcionService;

    @Autowired
    private AlumnoService alumnoService;

    @Autowired
    private CursoService cursoService;

    // --- 2. VARIABLES DE ESTADO ---
    private List<InscripcionDTO> inscripciones;
    private List<AlumnoDTO> alumnosDisponibles;
    private List<CursoDTO> cursosDisponibles;

    private InscripcionDTO inscripcionSeleccionada;
    private Long idAlumnoSeleccionado;
    private Long idCursoSeleccionado;
    private String estadoSeleccionado;
    private LocalDateTime fechaSeleccionada;

    private final List<String> estados = List.of("ACTIVA", "COMPLETADA", "CANCELADA");

    // --- 3. MÉTODOS DE CARGA ---
    @PostConstruct
    public void init() {
        cargarListasSoporte();
        cargarInscripciones();
    }

    private void cargarListasSoporte() {
        this.alumnosDisponibles = alumnoService.obtenerTodos();
        this.cursosDisponibles = cursoService.obtenerTodos();
    }

    private void cargarInscripciones() {
        this.inscripciones = inscripcionService.obtenerTodos();
    }

    public void prepararNuevaInscripcion() {
        cargarListasSoporte();
        this.inscripcionSeleccionada = new InscripcionDTO(null, null, LocalDateTime.now(), "ACTIVA");
        this.idAlumnoSeleccionado = null;
        this.idCursoSeleccionado = null;
        this.estadoSeleccionado = "ACTIVA";
        this.fechaSeleccionada = LocalDateTime.now();
    }

    // --- 4. MÉTODO seleccionarInscripcion ---
    public void seleccionarInscripcion(InscripcionDTO dto) {
        cargarListasSoporte();
        this.inscripcionSeleccionada = dto;

        this.idAlumnoSeleccionado = dto.idAlumno();
        this.idCursoSeleccionado = dto.idCurso();
        this.estadoSeleccionado = dto.estado();
        this.fechaSeleccionada = dto.fechaInscripcion();
    }

    // --- 5. MÉTODO guardarInscripcion ---
    public void guardarInscripcion() {
        try {
            if (idAlumnoSeleccionado == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Debe seleccionar un Alumno válido."));
                return;
            }

            if (idCursoSeleccionado == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Debe seleccionar un Curso válido."));
                return;
            }

            InscripcionDTO dtoAGuardar = new InscripcionDTO(
                    idAlumnoSeleccionado,
                    idCursoSeleccionado,
                    fechaSeleccionada,
                    estadoSeleccionado
            );

            if (inscripcionSeleccionada != null && inscripcionSeleccionada.idAlumno() != null && inscripcionSeleccionada.idCurso() != null) {

                Optional<InscripcionDTO> resultado = inscripcionService.modificarInscripcion(
                        inscripcionSeleccionada.idAlumno(),
                        inscripcionSeleccionada.idCurso(),
                        new ModInscripcionDTO(fechaSeleccionada, estadoSeleccionado)
                );

                if (resultado.isEmpty()) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se encontró la inscripción para modificar."));
                    return;
                }
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Inscripción modificada correctamente."));

            } else {
                inscripcionService.guardarInscripcion(dtoAGuardar);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Inscripción creada correctamente."));
            }

            cargarInscripciones();
            inscripcionSeleccionada = null;
            idAlumnoSeleccionado = null;
            idCursoSeleccionado = null;

        } catch (DataIntegrityViolationException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El alumno ya está inscrito en este curso o hay un problema de clave."));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ocurrió un error al guardar/modificar la inscripción: " + e.getMessage()));
            e.printStackTrace();
        }
    }

    // --- 6. MÉTODO eliminarInscripcion ---
    public void eliminarInscripcion() {
        if (inscripcionSeleccionada != null
                && inscripcionSeleccionada.idAlumno() != null
                && inscripcionSeleccionada.idCurso() != null) {
            try {
                inscripcionService.eliminarInscripcion(
                        inscripcionSeleccionada.idAlumno(),
                        inscripcionSeleccionada.idCurso()
                );

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminado", "La inscripción ha sido eliminada."));

                cargarInscripciones();
                inscripcionSeleccionada = null;

            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ocurrió un error inesperado al eliminar."));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "No se pudo eliminar la inscripción: seleccione una."));
        }
    }

    // --- 7. NUEVO MÉTODO DE FORMATO PARA XHTML (SOLUCIÓN AL ERROR DE CONVERSIÓN) ---
    /**
     * Convierte el LocalDateTime de la inscripción a un String con formato legible (dd/MM/yyyy HH:mm).
     * Esto evita los errores del conversor nativo de JSF con la API java.time.
     * @param i El DTO de Inscripción.
     * @return La fecha formateada como String, o una cadena vacía si es nulo.
     */
    public String getFechaInscripcionFormateada(InscripcionDTO i) {
        if (i == null || i.fechaInscripcion() == null) {
            return "";
        }

        // Define el formato deseado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        // Formatea el LocalDateTime a String y lo devuelve
        return i.fechaInscripcion().format(formatter);
    }
}