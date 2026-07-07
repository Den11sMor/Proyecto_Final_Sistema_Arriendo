package com.duoc.mssucursales.assemblers;

import com.duoc.mssucursales.controller.SucursalControllerV2;
import com.duoc.mssucursales.dto.SucursalDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

/**
 * Construye la representacion HATEOAS para las respuestas de sucursales
 */
@Component
public class SucursalModelAssembler implements RepresentationModelAssembler<SucursalDTO, EntityModel<SucursalDTO>> {

    @Override
    @NonNull
    public EntityModel<SucursalDTO> toModel(@NonNull SucursalDTO sucursal) {
        return EntityModel.of(sucursal,
                linkTo(methodOn(SucursalControllerV2.class).findById(sucursal.getId())).withSelfRel(),
                linkTo(methodOn(SucursalControllerV2.class).findAll()).withRel("sucursales"),
                linkTo(methodOn(SucursalControllerV2.class).listarOperativasOrdenadas()).withRel("sucursales-operativas"));
    }
}
