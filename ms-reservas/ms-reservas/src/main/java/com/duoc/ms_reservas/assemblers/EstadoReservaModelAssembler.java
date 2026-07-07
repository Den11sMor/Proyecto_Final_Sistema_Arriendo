package com.duoc.ms_reservas.assemblers;

import com.duoc.ms_reservas.controller.EstadoReservaControllerV2;
import com.duoc.ms_reservas.dto.EstadoReservaDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

/**
 * Construye la representacion HATEOAS para las respuestas de estados de reserva
 */
@Component
public class EstadoReservaModelAssembler implements RepresentationModelAssembler<EstadoReservaDTO, EntityModel<EstadoReservaDTO>> {

    @Override
    @NonNull
    public EntityModel<EstadoReservaDTO> toModel(@NonNull EstadoReservaDTO estado) {
        return EntityModel.of(estado,
                linkTo(methodOn(EstadoReservaControllerV2.class).findById(estado.getId())).withSelfRel(),
                linkTo(methodOn(EstadoReservaControllerV2.class).findAll()).withRel("estados-reserva"));
    }
}