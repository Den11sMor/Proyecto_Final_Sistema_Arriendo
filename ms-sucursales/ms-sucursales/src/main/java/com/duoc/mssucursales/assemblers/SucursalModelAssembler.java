package com.duoc.mssucursales.assemblers;

import com.duoc.mssucursales.dto.SucursalDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * Construye la representacion HATEOAS para las respuestas de sucursales
 */
@Component
public class SucursalModelAssembler implements RepresentationModelAssembler<SucursalDTO, EntityModel<SucursalDTO>> {

    @Override
    @NonNull
    public EntityModel<SucursalDTO> toModel(@NonNull SucursalDTO sucursal) {
        return EntityModel.of(sucursal,
                Link.of("/api/v2/sucursales/" + sucursal.getId()).withSelfRel(),
                Link.of("/api/v2/sucursales").withRel("sucursales"),
                Link.of("/api/v2/sucursales/operativas").withRel("sucursales-operativas"));
    }
}
