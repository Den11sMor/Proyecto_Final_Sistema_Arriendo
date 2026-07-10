package com.duoc.msvehiculos.controller;

import com.duoc.msvehiculos.assemblers.CategoriaModelAssembler;
import com.duoc.msvehiculos.dto.CategoriaDTO;
import com.duoc.msvehiculos.dto.CategoriaRequestDTO;
import com.duoc.msvehiculos.exception.ErrorResponse;
import com.duoc.msvehiculos.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * Controlador REST V2 para gestionar categorias con enlaces HATEOAS.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2")
@Slf4j
@Tag(name = "Categorias V2", description = "Operaciones de categorias con HATEOAS")
public class CategoriaControllerV2 {

    private final CategoriaService categoriaService;
    private final CategoriaModelAssembler categoriaModelAssembler;

    @GetMapping("/categorias")
    @Operation(summary = "Listar categorias con HATEOAS", description = "Obtiene todas las categorias con enlaces HATEOAS")
    @ApiResponse(responseCode = "200", description = "Categorias encontradas")
    public ResponseEntity<CollectionModel<EntityModel<CategoriaDTO>>> findAll() {
        log.info("Solicitud V2 para listar categorias");
        List<EntityModel<CategoriaDTO>> categorias = categoriaService.findAll()
                .stream()
                .map(categoriaModelAssembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(
                categorias,
                Link.of("/api/v2/categorias").withSelfRel()
        ));
    }

    @GetMapping("/categorias/{id}")
    @Operation(summary = "Buscar categoria por ID con HATEOAS", description = "Obtiene una categoria por su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria encontrada"),
            @ApiResponse(responseCode = "404", description = "Categoria no encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EntityModel<CategoriaDTO>> findById(@PathVariable Integer id) {
        log.info("Solicitud V2 para buscar categoria con id: {}", id);
        CategoriaDTO categoria = categoriaService.findById(id);
        return ResponseEntity.ok(categoriaModelAssembler.toModel(categoria));
    }

    @PostMapping("/categorias")
    @Operation(summary = "Crear categoria con HATEOAS", description = "Registra una categoria y retorna enlaces HATEOAS")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria creada"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EntityModel<CategoriaDTO>> save(@Valid @RequestBody CategoriaRequestDTO dto) {
        log.info("Solicitud V2 para crear categoria: {}", dto.getNombre());
        CategoriaDTO categoriaCreada = categoriaService.save(dto);

        return ResponseEntity
                .created(URI.create("/api/v2/categorias/" + categoriaCreada.getId()))
                .body(categoriaModelAssembler.toModel(categoriaCreada));
    }

    @PutMapping("/categorias/{id}")
    @Operation(summary = "Actualizar categoria con HATEOAS", description = "Actualiza una categoria por su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria actualizada"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Categoria no encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EntityModel<CategoriaDTO>> update(@PathVariable Integer id,
                                                            @Valid @RequestBody CategoriaRequestDTO dto) {
        log.info("Solicitud V2 para actualizar categoria con id: {}", id);
        CategoriaDTO categoriaActualizada = categoriaService.update(id, dto);
        return ResponseEntity.ok(categoriaModelAssembler.toModel(categoriaActualizada));
    }

    @DeleteMapping("/categorias/{id}")
    @Operation(summary = "Eliminar categoria", description = "Elimina una categoria por su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoria eliminada"),
            @ApiResponse(responseCode = "404", description = "Categoria no encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        log.info("Solicitud V2 para eliminar categoria con id: {}", id);
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
