package com.duoc.msempleados.assemblers;

import com.duoc.msempleados.dto.EmpleadoDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Ensamblador HATEOAS para agregar enlaces a las respuestas de empleados.
 */
@Component
public class EmpleadoModelAssembler implements RepresentationModelAssembler<EmpleadoDTO, EntityModel<EmpleadoDTO>> {

    @Override
    public @NonNull EntityModel<EmpleadoDTO> toModel(@NonNull EmpleadoDTO empleado) {
        EntityModel<EmpleadoDTO> model = EntityModel.of(
                empleado,
                Link.of("/api/v2/empleados/" + empleado.getId()).withSelfRel(),
                Link.of("/api/v2/empleados").withRel("empleados")
        );

        Integer anio = empleado.getFechaIngreso() != null
                ? empleado.getFechaIngreso().getYear()
                : LocalDate.now().getYear();

        model.add(Link.of("/api/v2/activos/anio/" + anio).withRel("empleados-activos-anio"));

        return model;
    }
}
