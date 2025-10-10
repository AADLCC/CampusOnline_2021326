package org.angel.cursoonline.platzi.web.controller;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject; // Cambiamos a inject estándar de Jakarta
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.angel.cursoonline.platzi.dominio.dto.CursoDTO;
import org.angel.cursoonline.platzi.dominio.dto.InstructorDTO;
import org.angel.cursoonline.platzi.dominio.dto.ModCursoDTO;
import org.angel.cursoonline.platzi.dominio.service.CursoService;
import org.angel.cursoonline.platzi.dominio.service.InstructorService;
import org.springframework.dao.DataIntegrityViolationException;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

// Renombramos el bean para que sea más corto y consistente
@Named("cursoController")
@ViewScoped
@Getter
@Setter
public class CursoVistaController implements Serializable {

    private static final long serialVersionUID = 1L;

    // --- Inyección de dependencias con el estándar de Jakarta EE ---
    @Inject
    private CursoService cursoService;
    @Inject
    private InstructorService instructorService;

    // --- Listas de datos ---
    private List<CursoDTO> cursos;
    private List<InstructorDTO> listaInstructores;

    // --- PROPIEDADES MUTABLES PARA EL FORMULARIO ---
    private Long idCursoForm;
    private Long idInstructorForm;
    private String nombreCursoForm;
    private String descripcionForm;
    private Integer duracionHorasForm;
    private BigDecimal precioForm;

    // Propiedad para el diálogo de eliminación
    private Long idCursoAEliminar;

    @PostConstruct
    public void init() {
        cargarCursos();
        // Cargamos la lista de instructores una vez para usarla en el ComboBox
        this.listaInstructores = instructorService.obtenerTodos();
    }

    public void cargarCursos() {
        this.cursos = cursoService.obtenerTodos();
    }

    // Prepara el formulario para un NUEVO curso
    public void prepararNuevoCurso() {
        this.idCursoForm = null; // Indica que es un nuevo registro
        this.idInstructorForm = null;
        this.nombreCursoForm = "";
        this.descripcionForm = "";
        this.duracionHorasForm = 0;
        this.precioForm = BigDecimal.ZERO;
    }

    // Carga los datos de un DTO existente en las propiedades del formulario para editarlo
    public void seleccionarCurso(CursoDTO curso) {
        this.idCursoForm = curso.idCurso();
        this.idInstructorForm = curso.idInstructor();
        this.nombreCursoForm = curso.nombreCurso();
        this.descripcionForm = curso.descripcion();
        this.duracionHorasForm = curso.duracionHoras();
        this.precioForm = curso.precio();
    }

    public void guardarCurso() {
        try {
            // Validamos que se haya seleccionado un instructor
            if (this.idInstructorForm == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Debe seleccionar un Instructor."));
                return; // Detiene la ejecución si no hay instructor
            }

            // Si el ID del formulario es null, es una CREACIÓN
            if (this.idCursoForm == null) {
                CursoDTO nuevoDto = new CursoDTO(
                        null,
                        this.idInstructorForm,
                        this.nombreCursoForm,
                        this.descripcionForm,
                        this.duracionHorasForm,
                        this.precioForm
                );
                cursoService.guardarCurso(nuevoDto);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Curso creado correctamente."));
            } else {
                // Si el ID tiene valor, es una ACTUALIZACIÓN
                ModCursoDTO modDto = new ModCursoDTO(
                        this.nombreCursoForm,
                        this.descripcionForm,
                        this.duracionHorasForm,
                        this.precioForm,
                        this.idInstructorForm
                );
                cursoService.modificarCurso(this.idCursoForm, modDto);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Curso actualizado correctamente."));
            }
            cargarCursos(); // Recargamos la lista para ver los cambios
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ocurrió un error al guardar el curso."));
        }
    }

    public void eliminarCurso() {
        if (this.idCursoAEliminar != null) {
            try {
                cursoService.eliminarCurso(this.idCursoAEliminar);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminado", "El curso ha sido eliminado."));
                cargarCursos();
                this.idCursoAEliminar = null; // Limpiar
            } catch (DataIntegrityViolationException e) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Acción Denegada", "No se puede eliminar. El curso tiene calificaciones o pagos asociados."));
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ocurrió un error inesperado al eliminar."));
            }
        }
    }
}