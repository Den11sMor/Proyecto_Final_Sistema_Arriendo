package com.duoc.msvehiculos.controller;

import com.duoc.msvehiculos.assemblers.CategoriaModelAssembler;
import com.duoc.msvehiculos.dto.CategoriaDTO;
import com.duoc.msvehiculos.dto.CategoriaRequestDTO;
import com.duoc.msvehiculos.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

/**
 * Controlador REST V2 para gestionar categorias con enlaces HATEOAS.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2")
@Tag(name = "Categorias V2", description = "Operaciones de categorias con HATEOAS")
public class CategoriaControllerV2 {

    private final CategoriaService categoriaService;
    private final CategoriaModelAssembler categoriaModelAssembler;

    @GetMapping("/categorias")
    @Operation(summary = "Listar categorias con HATEOAS")
    public ResponseEntity<CollectionModel<EntityModel<CategoriaDTO>>> findAll() {
        List<EntityModel<CategoriaDTO>> categorias = categoriaService.findAll()
                .stream()
                .map(categoriaModelAssembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(
                categorias,
                linkTo(methodOn(CategoriaControllerV2.class).findAll()).withSelfRel()
        ));
    }

    @GetMapping("/categorias/{id}")
    @Operation(summary = "Buscar categoria por ID con HATEOAS")
    public ResponseEntity<EntityModel<CategoriaDTO>> findById(@PathVariable Integer id) {
        CategoriaDTO categoria = categoriaService.findById(id);
        return ResponseEntity.ok(categoriaModelAssembler.toModel(categoria));
    }

    @PostMapping("/categorias")
    @Operation(summary = "Crear categoria con HATEOAS")
    public ResponseEntity<EntityModel<CategoriaDTO>> save(@Valid @RequestBody CategoriaRequestDTO dto) {
        CategoriaDTO categoriaCreada = categoriaService.save(dto);

        return ResponseEntity
                .created(linkTo(methodOn(CategoriaControllerV2.class).findById(categoriaCreada.getId())).toUri())
                .body(categoriaModelAssembler.toModel(categoriaCreada));
    }

    @PutMapping("/categorias/{id}")
    @Operation(summary = "Actualizar categoria con HATEOAS")
    public ResponseEntity<EntityModel<CategoriaDTO>> update(@PathVariable Integer id,
                                                            @Valid @RequestBody CategoriaRequestDTO dto) {
        CategoriaDTO categoriaActualizada = categoriaService.update(id, dto);
        return ResponseEntity.ok(categoriaModelAssembler.toModel(categoriaActualizada));
    }

    @DeleteMapping("/categorias/{id}")
    @Operation(summary = "Eliminar categoria")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}