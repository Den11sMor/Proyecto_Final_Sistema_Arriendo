package com.duoc.msempleados.assemblers;

import com.duoc.msempleados.controller.EmpleadoControllerV2;
import com.duoc.msempleados.dto.EmpleadoDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

/**
 * Ensamblador HATEOAS para agregar enlaces a las respuestas de empleados.
 */
@Component
public class EmpleadoModelAssembler implements RepresentationModelAssembler<EmpleadoDTO, EntityModel<EmpleadoDTO>> {

    @Override
    public @NonNull EntityModel<EmpleadoDTO> toModel(@NonNull EmpleadoDTO empleado) {
        EntityModel<EmpleadoDTO> model = EntityModel.of(
                empleado,
                linkTo(methodOn(EmpleadoControllerV2.class).findById(empleado.getId())).withSelfRel(),
                linkTo(methodOn(EmpleadoControllerV2.class).findAll()).withRel("empleados")
        );

        Integer anio = empleado.getFechaIngreso() != null
                ? empleado.getFechaIngreso().getYear()
                : LocalDate.now().getYear();

        model.add(linkTo(methodOn(EmpleadoControllerV2.class)
                .listarActivosPorAnio(anio)).withRel("empleados-activos-anio"));

        return model;
    }
}