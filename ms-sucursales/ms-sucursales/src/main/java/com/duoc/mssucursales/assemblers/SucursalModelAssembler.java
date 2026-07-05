package com.duoc.mssucursales.assemblers;

import com.duoc.mssucursales.controller.RegionControllerV2;
import com.duoc.mssucursales.controller.SucursalControllerV2;
import com.duoc.mssucursales.dto.SucursalDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class SucursalModelAssembler implements RepresentationModelAssembler<SucursalDTO, EntityModel<SucursalDTO>> {

    @Override
    public EntityModel<SucursalDTO> toModel(SucursalDTO sucursal) {
        EntityModel<SucursalDTO> model = EntityModel.of(sucursal,
                linkTo(methodOn(SucursalControllerV2.class).findById(sucursal.getId())).withSelfRel(),
                linkTo(methodOn(SucursalControllerV2.class).findAll()).withRel("sucursales"),
                linkTo(methodOn(SucursalControllerV2.class).listarOperativasOrdenadas()).withRel("sucursales-operativas"));

        if (sucursal.getRegionId() != null) {
            model.add(linkTo(methodOn(RegionControllerV2.class)
                    .findById(sucursal.getRegionId())).withRel("region"));
        }

        return model;
    }
}
