package com.duoc.ms_pagos.assemblers;

import com.duoc.ms_pagos.dto.PagoDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * Ensamblador HATEOAS para agregar enlaces a las respuestas de pagos.
 */
@Component
public class PagoModelAssembler implements RepresentationModelAssembler<PagoDTO, EntityModel<PagoDTO>> {

    @Override
    public @NonNull EntityModel<PagoDTO> toModel(@NonNull PagoDTO pago) {
        return EntityModel.of(
                pago,
                Link.of("/api/v2/pagos/" + pago.getId()).withSelfRel(),
                Link.of("/api/v2/pagos").withRel("pagos"),
                Link.of("/api/v2/pagos/rango?min=0&max=100000").withRel("pagos-rango")
        );
    }
}
