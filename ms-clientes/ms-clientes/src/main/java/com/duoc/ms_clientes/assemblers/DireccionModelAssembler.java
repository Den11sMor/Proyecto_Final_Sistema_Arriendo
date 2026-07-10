package com.duoc.ms_clientes.assemblers;

import com.duoc.ms_clientes.dto.DireccionDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * Ensamblador HATEOAS para agregar enlaces a las respuestas de direcciones.
 */
@Component
public class DireccionModelAssembler implements RepresentationModelAssembler<DireccionDTO, EntityModel<DireccionDTO>> {

    @Override
    public @NonNull EntityModel<DireccionDTO> toModel(@NonNull DireccionDTO direccion) {
        return EntityModel.of(
                direccion,
                Link.of("/api/v2/direcciones/" + direccion.getId()).withSelfRel(),
                Link.of("/api/v2/direcciones").withRel("direcciones")
        );
    }
}
