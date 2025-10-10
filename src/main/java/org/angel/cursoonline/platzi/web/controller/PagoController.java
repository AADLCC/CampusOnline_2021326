package org.angel.cursoonline.platzi.web.controller;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.angel.cursoonline.platzi.dominio.dto.AlumnoDTO;
import org.angel.cursoonline.platzi.dominio.dto.CursoDTO;
import org.angel.cursoonline.platzi.dominio.dto.PagoDTO;
import org.angel.cursoonline.platzi.dominio.service.AlumnoService;
import org.angel.cursoonline.platzi.dominio.service.CursoService;
import org.angel.cursoonline.platzi.dominio.service.PagoService;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Named("pagoController")
@ViewScoped
@Getter
@Setter
public class PagoController implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<PagoDTO> pagos;

    // --- PROPIEDADES MUTABLES PARA EL FORMULARIO ---
    private Long idPagoForm; // Para diferenciar entre crear y editar
    private Long idAlumnoForm;
    private Long idCursoForm;
    private Double montoForm;
    private String metodoPagoForm;
    private LocalDateTime fechaPagoOriginal;

    // --- Listas para los ComboBoxes ---
    private List<AlumnoDTO> listaAlumnos;
    private List<CursoDTO> listaCursos;

    // Propiedad para el diálogo de eliminación
    private Long idPagoAEliminar;

    // --- Inyección de todos los servicios necesarios ---
    @Inject
    private PagoService pagoService;
    @Inject
    private AlumnoService alumnoService;
    @Inject
    private CursoService cursoService;

    @PostConstruct
    public void init() {
        cargarPagos();
        // Cargamos los datos para los desplegables una sola vez
        this.listaAlumnos = alumnoService.obtenerTodos();
        this.listaCursos = cursoService.obtenerTodos();
    }

    public void cargarPagos() {
        pagos = pagoService.obtenerTodos();
    }

    // Prepara el formulario para un NUEVO pago
    public void prepararNuevoPago() {
        this.idPagoForm = null;
        this.idAlumnoForm = null;
        this.idCursoForm = null;
        this.montoForm = 0.0;
        this.metodoPagoForm = null;
        this.fechaPagoOriginal = null;
    }

    // Carga los datos de un pago existente en las propiedades del formulario
    public void seleccionarPago(PagoDTO pago) {
        this.idPagoForm = pago.idPago();
        this.idAlumnoForm = pago.idAlumno();
        this.idCursoForm = pago.idCurso();
        this.montoForm = pago.monto();
        this.metodoPagoForm = pago.metodoPago();
        this.fechaPagoOriginal = pago.fechaPago();
    }

    public void guardarPago() {
        try {
            // Si es un pago nuevo, se usa la fecha actual. Si se edita, se conserva la original.
            LocalDateTime fecha = (this.fechaPagoOriginal != null) ? this.fechaPagoOriginal : LocalDateTime.now();

            // Construimos el DTO inmutable a partir de los datos del formulario
            PagoDTO pagoAGuardar = new PagoDTO(
                    this.idPagoForm, // Será null para un nuevo pago, o un ID para editar
                    this.idAlumnoForm,
                    this.idCursoForm,
                    this.montoForm,
                    fecha,
                    this.metodoPagoForm
            );

            pagoService.guardarPago(pagoAGuardar);
            cargarPagos(); // Recargar la tabla

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Pago guardado correctamente."));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo guardar el pago: " + e.getMessage()));
        }
    }

    public void eliminarPago() {
        if (this.idPagoAEliminar == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "No se ha seleccionado un pago para eliminar."));
            return;
        }

        try {
            pagoService.eliminarPago(this.idPagoAEliminar);
            cargarPagos();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminado", "Pago eliminado correctamente."));
            this.idPagoAEliminar = null; // Limpiar la variable
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo eliminar el pago."));
        }
    }
}