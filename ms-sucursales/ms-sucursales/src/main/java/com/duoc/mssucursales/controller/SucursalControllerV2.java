package com.duoc.mssucursales.controller;

import com.duoc.mssucursales.assemblers.SucursalModelAssembler;
import com.duoc.mssucursales.dto.SucursalDTO;
import com.duoc.mssucursales.dto.SucursalRequestDTO;
import com.duoc.mssucursales.exception.ErrorResponse;
import com.duoc.mssucursales.service.SucursalService;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2/sucursales")
@Slf4j
@Tag(name = "Sucursales V2", description = "Operaciones de sucursales con HATEOAS")
public class SucursalControllerV2 {

    private final SucursalService sucursalService;
    private final SucursalModelAssembler sucursalModelAssembler;

    @GetMapping
    @Operation(summary = "Listar sucursales con HATEOAS", description = "Retorna todas las sucursales con enlaces relacionados")
    @ApiResponse(responseCode = "200", description = "Sucursales encontradas")
    public ResponseEntity<CollectionModel<EntityModel<SucursalDTO>>> findAll() {
        log.info("Solicitud V2 para listar sucursales");
        List<EntityModel<SucursalDTO>> sucursales = sucursalService.findAll()
                .stream()
                .map(sucursalModelAssembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(
                sucursales,
                Link.of("/api/v2/sucursales").withSelfRel()
        ));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar sucursal por ID con HATEOAS", description = "Retorna una sucursal con enlaces relacionados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucursal encontrada"),
            @ApiResponse(responseCode = "404", description = "Sucursal no encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EntityModel<SucursalDTO>> findById(@PathVariable Integer id) {
        log.info("Solicitud V2 para buscar sucursal con id: {}", id);
        SucursalDTO sucursal = sucursalService.findById(id);
        return ResponseEntity.ok(sucursalModelAssembler.toModel(sucursal));
    }

    @PostMapping
    @Operation(summary = "Crear sucursal con HATEOAS", description = "Registra una sucursal y retorna enlaces HATEOAS")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sucursal creada"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Region no encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EntityModel<SucursalDTO>> save(@Valid @RequestBody SucursalRequestDTO requestDTO) {
        log.info("Solicitud V2 para crear sucursal: {}", requestDTO.getNombre());
        SucursalDTO sucursalCreada = sucursalService.save(requestDTO);

        return ResponseEntity
                .created(URI.create("/api/v2/sucursales/" + sucursalCreada.getId()))
                .body(sucursalModelAssembler.toModel(sucursalCreada));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar sucursal con HATEOAS", description = "Actualiza una sucursal y retorna enlaces HATEOAS")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucursal actualizada"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Sucursal o region no encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EntityModel<SucursalDTO>> update(@PathVariable Integer id,
                                                           @Valid @RequestBody SucursalRequestDTO requestDTO) {
        log.info("Solicitud V2 para actualizar sucursal con id: {}", id);
        SucursalDTO sucursalActualizada = sucursalService.update(id, requestDTO);
        return ResponseEntity.ok(sucursalModelAssembler.toModel(sucursalActualizada));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar sucursal", description = "Elimina una sucursal por su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Sucursal eliminada"),
            @ApiResponse(responseCode = "404", description = "Sucursal no encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        log.info("Solicitud V2 para eliminar sucursal con id: {}", id);
        sucursalService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/operativas")
    @Operation(summary = "Listar sucursales operativas con HATEOAS",
            description = "Retorna las sucursales operativas ordenadas con enlaces relacionados")
    @ApiResponse(responseCode = "200", description = "Sucursales operativas encontradas")
    public ResponseEntity<CollectionModel<EntityModel<SucursalDTO>>> listarOperativasOrdenadas() {
        log.info("Solicitud V2 para listar sucursales operativas");
        List<EntityModel<SucursalDTO>> sucursales = sucursalService.listarOperativasOrdenadas()
                .stream()
                .map(sucursalModelAssembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(
                sucursales,
                Link.of("/api/v2/sucursales/operativas").withSelfRel(),
                Link.of("/api/v2/sucursales").withRel("sucursales")
        ));
    }
}
