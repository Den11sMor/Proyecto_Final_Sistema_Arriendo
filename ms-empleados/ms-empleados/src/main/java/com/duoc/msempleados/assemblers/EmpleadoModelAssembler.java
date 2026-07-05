package com.duoc.msempleados.assemblers;

import com.duoc.msempleados.controller.EmpleadoControllerV2;
import com.duoc.msempleados.dto.EmpleadoDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class EmpleadoModelAssembler implements RepresentationModelAssembler<EmpleadoDTO, EntityModel<EmpleadoDTO>> {

    @Override
    public EntityModel<EmpleadoDTO> toModel(EmpleadoDTO empleado) {
        return EntityModel.of(empleado,
                linkTo(methodOn(EmpleadoControllerV2.class).findById(empleado.getId())).withSelfRel(),
                linkTo(methodOn(EmpleadoControllerV2.class).findAll()).withRel("empleados"));
    }
}