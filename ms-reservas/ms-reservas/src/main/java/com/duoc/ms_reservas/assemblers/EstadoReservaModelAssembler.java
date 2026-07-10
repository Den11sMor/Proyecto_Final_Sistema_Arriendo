package com.duoc.ms_reservas.assemblers;

import com.duoc.ms_reservas.dto.EstadoReservaDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * Construye la representacion HATEOAS para las respuestas de estados de reserva
 */
@Component
public class EstadoReservaModelAssembler implements RepresentationModelAssembler<EstadoReservaDTO, EntityModel<EstadoReservaDTO>> {

    @Override
    @NonNull
    public EntityModel<EstadoReservaDTO> toModel(@NonNull EstadoReservaDTO estado) {
        return EntityModel.of(estado,
                Link.of("/api/v2/estados-reserva/" + estado.getId()).withSelfRel(),
                Link.of("/api/v2/estados-reserva").withRel("estados-reserva"));
    }
}
