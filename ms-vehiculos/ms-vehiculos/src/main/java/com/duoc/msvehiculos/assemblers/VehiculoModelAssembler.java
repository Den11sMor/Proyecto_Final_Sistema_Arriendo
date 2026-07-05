package com.duoc.msvehiculos.assemblers;

import com.duoc.msvehiculos.controller.CategoriaControllerV2;
import com.duoc.msvehiculos.controller.VehiculoControllerV2;
import com.duoc.msvehiculos.dto.VehiculoDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class VehiculoModelAssembler implements RepresentationModelAssembler<VehiculoDTO, EntityModel<VehiculoDTO>> {

    @Override
    public EntityModel<VehiculoDTO> toModel(VehiculoDTO vehiculo) {
        EntityModel<VehiculoDTO> model = EntityModel.of(vehiculo,
                linkTo(methodOn(VehiculoControllerV2.class).findById(vehiculo.getId())).withSelfRel(),
                linkTo(methodOn(VehiculoControllerV2.class).findAll()).withRel("vehiculos"),
                linkTo(methodOn(VehiculoControllerV2.class)
                        .buscarDisponiblesPorPrecioMenor(BigDecimal.valueOf(50000)))
                        .withRel("vehiculos-disponibles-precio-menor"));

        if (vehiculo.getCategoriaId() != null) {
            model.add(linkTo(methodOn(CategoriaControllerV2.class)
                    .findById(vehiculo.getCategoriaId())).withRel("categoria"));
        }

        return model;
    }
}