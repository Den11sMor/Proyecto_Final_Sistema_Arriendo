package com.duoc.ms_clientes.assemblers;

import com.duoc.ms_clientes.dto.ClienteDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * Ensamblador HATEOAS para agregar enlaces a las respuestas de clientes.
 */
@Component
public class ClienteModelAssembler implements RepresentationModelAssembler<ClienteDTO, EntityModel<ClienteDTO>> {

    @Override
    public @NonNull EntityModel<ClienteDTO> toModel(@NonNull ClienteDTO cliente) {
        return EntityModel.of(
                cliente,
                Link.of("/api/v2/clientes/" + cliente.getId()).withSelfRel(),
                Link.of("/api/v2/clientes").withRel("clientes")
        );
    }
}
