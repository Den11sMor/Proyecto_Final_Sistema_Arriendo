package com.duoc.ms_clientes.assemblers;

import com.duoc.ms_clientes.controller.DireccionControllerV2;
import com.duoc.ms_clientes.dto.DireccionDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

/**
 * Ensamblador HATEOAS para agregar enlaces a las respuestas de direcciones.
 */
@Component
public class DireccionModelAssembler implements RepresentationModelAssembler<DireccionDTO, EntityModel<DireccionDTO>> {

    @Override
    public @NonNull EntityModel<DireccionDTO> toModel(@NonNull DireccionDTO direccion) {
        return EntityModel.of(
                direccion,
                linkTo(methodOn(DireccionControllerV2.class).findById(direccion.getId())).withSelfRel(),
                linkTo(methodOn(DireccionControllerV2.class).findAll()).withRel("direcciones")
        );
    }
}