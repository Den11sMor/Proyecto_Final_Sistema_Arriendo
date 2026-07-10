package com.duoc.msvehiculos.assemblers;

import com.duoc.msvehiculos.dto.CategoriaDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * Ensamblador HATEOAS para agregar enlaces a las respuestas de categorias.
 */
@Component
public class CategoriaModelAssembler implements RepresentationModelAssembler<CategoriaDTO, EntityModel<CategoriaDTO>> {

    @Override
    public @NonNull EntityModel<CategoriaDTO> toModel(@NonNull CategoriaDTO categoria) {
        return EntityModel.of(
                categoria,
                Link.of("/api/v2/categorias/" + categoria.getId()).withSelfRel(),
                Link.of("/api/v2/categorias").withRel("categorias")
        );
    }
}
