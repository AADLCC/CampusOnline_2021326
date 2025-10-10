package org.angel.cursoonline.platzi.dominio.service;

import org.angel.cursoonline.platzi.dominio.dto.PagoDTO;
import org.angel.cursoonline.platzi.dominio.repository.PagoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PagoService {

    private final PagoRepository pagoRepository;

    public PagoService(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }

    public List<PagoDTO> obtenerTodos() {
        return pagoRepository.obtenerTodos();
    }

    public Optional<PagoDTO> buscarPorID(Long idPago) {
        return pagoRepository.buscarPorID(idPago);
    }

    public Optional<PagoDTO> guardarPago(PagoDTO pagoDTO) {
        return pagoRepository.guardarPago(pagoDTO);
    }

    public void eliminarPago(Long idPago) {
        pagoRepository.eliminarPago(idPago);
    }
}
