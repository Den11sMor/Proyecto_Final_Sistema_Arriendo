package com.duoc.msvehiculos.controller;

import com.duoc.msvehiculos.assemblers.CategoriaModelAssembler;
import com.duoc.msvehiculos.dto.CategoriaDTO;
import com.duoc.msvehiculos.dto.CategoriaRequestDTO;
import com.duoc.msvehiculos.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2")
public class CategoriaControllerV2 {

    private final CategoriaService categoriaService;
    private final CategoriaModelAssembler categoriaModelAssembler;

    @GetMapping("/categorias")
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
    public ResponseEntity<EntityModel<CategoriaDTO>> findById(@PathVariable Integer id) {
        CategoriaDTO categoria = categoriaService.findById(id);
        return ResponseEntity.ok(categoriaModelAssembler.toModel(categoria));
    }

    @PostMapping("/categorias")
    public ResponseEntity<EntityModel<CategoriaDTO>> save(@Valid @RequestBody CategoriaRequestDTO dto) {
        CategoriaDTO categoriaCreada = categoriaService.save(dto);

        return ResponseEntity
                .created(linkTo(methodOn(CategoriaControllerV2.class).findById(categoriaCreada.getId())).toUri())
                .body(categoriaModelAssembler.toModel(categoriaCreada));
    }

    @PutMapping("/categorias/{id}")
    public ResponseEntity<EntityModel<CategoriaDTO>> update(@PathVariable Integer id,
                                                            @Valid @RequestBody CategoriaRequestDTO dto) {
        CategoriaDTO categoriaActualizada = categoriaService.update(id, dto);
        return ResponseEntity.ok(categoriaModelAssembler.toModel(categoriaActualizada));
    }

    @DeleteMapping("/categorias/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}