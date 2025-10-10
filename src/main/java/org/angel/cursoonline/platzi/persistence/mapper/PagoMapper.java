package org.angel.cursoonline.platzi.persistence.mapper;

import org.angel.cursoonline.platzi.dominio.dto.PagoDTO;
import org.angel.cursoonline.platzi.persistence.entity.Pago;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PagoMapper {

    // --- Mapeo de DTO a Entidad ---

    // La conversión es directa; el DTO y la Entidad tienen nombres de campo que coinciden
    Pago toPago(PagoDTO dto);

    // --- Mapeo de Entidad a DTO ---

    @InheritInverseConfiguration
    PagoDTO toPagoDTO(Pago entity);

    List<PagoDTO> toPagoDTOs(List<Pago> entities);
}
