package org.angel.cursoonline.platzi.persistence;

import org.angel.cursoonline.platzi.dominio.dto.PagoDTO;
import org.angel.cursoonline.platzi.dominio.repository.PagoRepository;
import org.angel.cursoonline.platzi.persistence.crud.CrudPagoEntity;
import org.angel.cursoonline.platzi.persistence.entity.Pago;
import org.angel.cursoonline.platzi.persistence.mapper.PagoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PagoEntityRepository implements PagoRepository {

    @Autowired
    private CrudPagoEntity crudPagoEntity;

    @Autowired
    private PagoMapper pagoMapper;

    @Override
    public List<PagoDTO> obtenerTodos() {
        List<Pago> pagos = crudPagoEntity.findAll();
        return pagoMapper.toPagoDTOs(pagos);
    }

    @Override
    public Optional<PagoDTO> buscarPorID(Long idPago) {
        return crudPagoEntity.findById(idPago)
                .map(pagoMapper::toPagoDTO);
    }

    @Override
    public Optional<PagoDTO> guardarPago(PagoDTO pagoDTO) {
        Pago pago = pagoMapper.toPago(pagoDTO);
        Pago guardado = crudPagoEntity.save(pago);
        return Optional.of(pagoMapper.toPagoDTO(guardado));
    }

    @Override
    public void eliminarPago(Long idPago) {
        crudPagoEntity.deleteById(idPago);
    }
}
