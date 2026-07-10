package com.duoc.ms_reservas.assemblers;

import com.duoc.ms_reservas.dto.ReservaDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * Construye la representacion HATEOAS para las respuestas de reservas
 */
@Component
public class ReservaModelAssembler implements RepresentationModelAssembler<ReservaDTO, EntityModel<ReservaDTO>> {

    @Override
    @NonNull
    public EntityModel<ReservaDTO> toModel(@NonNull ReservaDTO reserva) {
        EntityModel<ReservaDTO> model = EntityModel.of(reserva,
                Link.of("/api/v2/reservas/" + reserva.getId()).withSelfRel(),
                Link.of("/api/v2/reservas").withRel("reservas"));

        if (reserva.getEstadoReservaId() != null) {
            model.add(Link.of("/api/v2/estados-reserva/" + reserva.getEstadoReservaId()).withRel("estado-reserva"));
        }

        return model;
    }
}
