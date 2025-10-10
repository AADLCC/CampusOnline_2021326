package org.angel.cursoonline.platzi.dominio.repository;

import org.angel.cursoonline.platzi.dominio.dto.PagoDTO;
import java.util.List;
import java.util.Optional;

public interface PagoRepository {

    List<PagoDTO> obtenerTodos();

    Optional<PagoDTO> buscarPorID(Long idPago);

    Optional<PagoDTO> guardarPago(PagoDTO pagoDTO);

    void eliminarPago(Long idPago);
}
