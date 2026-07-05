package com.duoc.ms_clientes.assemblers;

import com.duoc.ms_clientes.controller.ClienteController;
import com.duoc.ms_clientes.dto.ClienteDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ClienteModelAssembler implements RepresentationModelAssembler<ClienteDTO, EntityModel<ClienteDTO>> {

    @Override
    public EntityModel<ClienteDTO> toModel(ClienteDTO cliente) {
        return EntityModel.of(cliente,
                linkTo(methodOn(ClienteController.class).findById(cliente.getId())).withSelfRel(),
                linkTo(methodOn(ClienteController.class).findAll()).withRel("clientes"));
    }
}
