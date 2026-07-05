package com.duoc.mssucursales.assemblers;

import com.duoc.mssucursales.controller.RegionControllerV2;
import com.duoc.mssucursales.dto.RegionDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class RegionModelAssembler implements RepresentationModelAssembler<RegionDTO, EntityModel<RegionDTO>> {

    @Override
    public EntityModel<RegionDTO> toModel(RegionDTO region) {
        return EntityModel.of(region,
                linkTo(methodOn(RegionControllerV2.class).findById(region.getId())).withSelfRel(),
                linkTo(methodOn(RegionControllerV2.class).findAll()).withRel("regiones"));
    }
}
