package com.duoc.msvehiculos.assemblers;

import com.duoc.msvehiculos.dto.VehiculoDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * Ensamblador HATEOAS para agregar enlaces a las respuestas de vehiculos.
 */
@Component
public class VehiculoModelAssembler implements RepresentationModelAssembler<VehiculoDTO, EntityModel<VehiculoDTO>> {

    @Override
    public @NonNull EntityModel<VehiculoDTO> toModel(@NonNull VehiculoDTO vehiculo) {
        EntityModel<VehiculoDTO> model = EntityModel.of(
                vehiculo,
                Link.of("/api/v2/vehiculos/" + vehiculo.getId()).withSelfRel(),
                Link.of("/api/v2/vehiculos").withRel("vehiculos"),
                Link.of("/api/v2/vehiculos/disponibles/precio-menor/50000")
                        .withRel("vehiculos-disponibles-precio-menor")
        );

        if (vehiculo.getCategoriaId() != null) {
            model.add(Link.of("/api/v2/categorias/" + vehiculo.getCategoriaId()).withRel("categoria"));
        }

        return model;
    }
}
