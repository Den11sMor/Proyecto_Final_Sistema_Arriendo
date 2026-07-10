package com.duoc.mssucursales.assemblers;

import com.duoc.mssucursales.dto.RegionDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * Construye la representacion HATEOAS para las respuestas de regiones
 */
@Component
public class RegionModelAssembler implements RepresentationModelAssembler<RegionDTO, EntityModel<RegionDTO>> {

    @Override
    @NonNull
    public EntityModel<RegionDTO> toModel(@NonNull RegionDTO region) {
        return EntityModel.of(region,
                Link.of("/api/v2/regiones/" + region.getId()).withSelfRel(),
                Link.of("/api/v2/regiones").withRel("regiones"));
    }
}
