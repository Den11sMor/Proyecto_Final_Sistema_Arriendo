package com.duoc.ms_pagos.assemblers;

import com.duoc.ms_pagos.controller.PagoControllerV2;
import com.duoc.ms_pagos.dto.PagoDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class PagoModelAssembler implements RepresentationModelAssembler<PagoDTO, EntityModel<PagoDTO>> {

    @Override
    public EntityModel<PagoDTO> toModel(PagoDTO pago) {
        return EntityModel.of(pago,
                linkTo(methodOn(PagoControllerV2.class).findById(pago.getId())).withSelfRel(),
                linkTo(methodOn(PagoControllerV2.class).findAll()).withRel("pagos"),
                linkTo(methodOn(PagoControllerV2.class)
                        .buscarPorRangoMonto(BigDecimal.ZERO, BigDecimal.valueOf(100000)))
                        .withRel("pagos-rango"));
    }
}