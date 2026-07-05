package com.duoc.ms_reservas.assemblers;

import com.duoc.ms_reservas.controller.EstadoReservaControllerV2;
import com.duoc.ms_reservas.controller.ReservaControllerV2;
import com.duoc.ms_reservas.dto.ReservaDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ReservaModelAssembler implements RepresentationModelAssembler<ReservaDTO, EntityModel<ReservaDTO>> {

    @Override
    public EntityModel<ReservaDTO> toModel(ReservaDTO reserva) {
        EntityModel<ReservaDTO> model = EntityModel.of(reserva,
                linkTo(methodOn(ReservaControllerV2.class).findById(reserva.getId())).withSelfRel(),
                linkTo(methodOn(ReservaControllerV2.class).findAll()).withRel("reservas"));

        if (reserva.getEstadoReservaId() != null) {
            model.add(linkTo(methodOn(EstadoReservaControllerV2.class)
                    .findById(reserva.getEstadoReservaId())).withRel("estado-reserva"));
        }

        return model;
    }
}