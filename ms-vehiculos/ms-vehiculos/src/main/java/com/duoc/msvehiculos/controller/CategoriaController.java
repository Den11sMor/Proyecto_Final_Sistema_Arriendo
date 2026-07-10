package com.duoc.msvehiculos.controller;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST V1 para gestionar categorias de vehiculos.
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Categorias V1", description = "Operaciones CRUD de categorias de vehiculos")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping("/categorias")
    @Operation(summary = "Listar categorias", description = "Obtiene todas las categorias registradas")
    @ApiResponse(responseCode = "200", description = "Categorias encontradas")
    public ResponseEntity<List<CategoriaDTO>> findAll() {
        log.info("Solicitud para listar categorias");
        return ResponseEntity.ok(categoriaService.findAll());
    }

    @GetMapping("/categorias/{id}")
    @Operation(summary = "Buscar categoria por ID", description = "Obtiene una categoria segun su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria encontrada",
                    content = @Content(schema = @Schema(implementation = CategoriaDTO.class))),
            @ApiResponse(responseCode = "404", description = "Categoria no encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<CategoriaDTO> findById(@PathVariable Integer id) {
        log.info("Solicitud para buscar categoria con id: {}", id);
        return ResponseEntity.ok(categoriaService.findById(id));
    }

    @PostMapping("/categorias")
    @Operation(summary = "Crear categoria", description = "Registra una nueva categoria de vehiculo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria creada",
                    content = @Content(schema = @Schema(implementation = CategoriaDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos invalidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<CategoriaDTO> save(@Valid @RequestBody CategoriaRequestDTO dto) {
        log.info("Solicitud para crear categoria: {}", dto.getNombre());
        CategoriaDTO categoriaCreada = categoriaService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaCreada);
    }

    @PutMapping("/categorias/{id}")
    @Operation(summary = "Actualizar categoria", description = "Actualiza una categoria existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria actualizada",
                    content = @Content(schema = @Schema(implementation = CategoriaDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos invalidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Categoria no encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<CategoriaDTO> update(@PathVariable Integer id,
                                               @Valid @RequestBody CategoriaRequestDTO dto) {
        log.info("Solicitud para actualizar categoria con id: {}", id);
        CategoriaDTO categoriaActualizada = categoriaService.update(id, dto);
        return ResponseEntity.ok(categoriaActualizada);
    }

    @DeleteMapping("/categorias/{id}")
    @Operation(summary = "Eliminar categoria", description = "Elimina una categoria por su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoria eliminada"),
            @ApiResponse(responseCode = "404", description = "Categoria no encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        log.info("Solicitud para eliminar categoria con id: {}", id);
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
