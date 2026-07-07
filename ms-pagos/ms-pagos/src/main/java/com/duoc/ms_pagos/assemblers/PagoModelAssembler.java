package com.duoc.ms_pagos.assemblers;

import com.duoc.ms_pagos.controller.PagoControllerV2;
import com.duoc.ms_pagos.dto.PagoDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

/**
 * Ensamblador HATEOAS para agregar enlaces a las respuestas de pagos.
 */
@Component
public class PagoModelAssembler implements RepresentationModelAssembler<PagoDTO, EntityModel<PagoDTO>> {

    @Override
    public @NonNull EntityModel<PagoDTO> toModel(@NonNull PagoDTO pago) {
        return EntityModel.of(
                pago,
                linkTo(methodOn(PagoControllerV2.class).findById(pago.getId())).withSelfRel(),
                linkTo(methodOn(PagoControllerV2.class).findAll()).withRel("pagos"),
                linkTo(methodOn(PagoControllerV2.class)
                        .buscarPorRangoMonto(BigDecimal.ZERO, BigDecimal.valueOf(100000)))
                        .withRel("pagos-rango")
        );
    }
}