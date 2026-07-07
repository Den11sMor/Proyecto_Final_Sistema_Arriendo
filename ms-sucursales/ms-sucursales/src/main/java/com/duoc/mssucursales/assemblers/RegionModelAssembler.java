package com.duoc.mssucursales.assemblers;

import com.duoc.mssucursales.controller.RegionControllerV2;
import com.duoc.mssucursales.dto.RegionDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

/**
 * Construye la representacion HATEOAS para las respuestas de regiones
 */
@Component
public class RegionModelAssembler implements RepresentationModelAssembler<RegionDTO, EntityModel<RegionDTO>> {

    @Override
    @NonNull
    public EntityModel<RegionDTO> toModel(@NonNull RegionDTO region) {
        return EntityModel.of(region,
                linkTo(methodOn(RegionControllerV2.class).findById(region.getId())).withSelfRel(),
                linkTo(methodOn(RegionControllerV2.class).findAll()).withRel("regiones"));
    }
}