package com.duoc.msvehiculos.assemblers;

import com.duoc.msvehiculos.controller.CategoriaControllerV2;
import com.duoc.msvehiculos.dto.CategoriaDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

/**
 * Ensamblador HATEOAS para agregar enlaces a las respuestas de categorias.
 */
@Component
public class CategoriaModelAssembler implements RepresentationModelAssembler<CategoriaDTO, EntityModel<CategoriaDTO>> {

    @Override
    public @NonNull EntityModel<CategoriaDTO> toModel(@NonNull CategoriaDTO categoria) {
        return EntityModel.of(
                categoria,
                linkTo(methodOn(CategoriaControllerV2.class).findById(categoria.getId())).withSelfRel(),
                linkTo(methodOn(CategoriaControllerV2.class).findAll()).withRel("categorias")
        );
    }
}