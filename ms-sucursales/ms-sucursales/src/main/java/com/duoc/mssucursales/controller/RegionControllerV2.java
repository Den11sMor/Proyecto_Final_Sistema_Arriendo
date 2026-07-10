package com.duoc.mssucursales.controller;

import com.duoc.mssucursales.assemblers.RegionModelAssembler;
import com.duoc.mssucursales.dto.RegionDTO;
import com.duoc.mssucursales.dto.RegionRequestDTO;
import com.duoc.mssucursales.exception.ErrorResponse;
import com.duoc.mssucursales.service.RegionService;
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
@RequestMapping("/api/v2/regiones")
@Slf4j
@Tag(name = "Regiones V2", description = "Operaciones de regiones con HATEOAS")
public class RegionControllerV2 {

    private final RegionService regionService;
    private final RegionModelAssembler regionModelAssembler;

    @GetMapping
    @Operation(summary = "Listar regiones con HATEOAS", description = "Retorna todas las regiones con enlaces relacionados")
    @ApiResponse(responseCode = "200", description = "Regiones encontradas")
    public ResponseEntity<CollectionModel<EntityModel<RegionDTO>>> findAll() {
        log.info("Solicitud V2 para listar regiones");
        List<EntityModel<RegionDTO>> regiones = regionService.findAll()
                .stream()
                .map(regionModelAssembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(
                regiones,
                Link.of("/api/v2/regiones").withSelfRel()
        ));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar region por ID con HATEOAS", description = "Retorna una region con enlaces relacionados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Region encontrada"),
            @ApiResponse(responseCode = "404", description = "Region no encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EntityModel<RegionDTO>> findById(@PathVariable Integer id) {
        log.info("Solicitud V2 para buscar region con id: {}", id);
        RegionDTO region = regionService.findById(id);
        return ResponseEntity.ok(regionModelAssembler.toModel(region));
    }

    @PostMapping
    @Operation(summary = "Crear region con HATEOAS", description = "Registra una region y retorna enlaces HATEOAS")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Region creada"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EntityModel<RegionDTO>> save(@Valid @RequestBody RegionRequestDTO requestDTO) {
        log.info("Solicitud V2 para crear region: {}", requestDTO.getNombre());
        RegionDTO regionCreada = regionService.save(requestDTO);

        return ResponseEntity
                .created(URI.create("/api/v2/regiones/" + regionCreada.getId()))
                .body(regionModelAssembler.toModel(regionCreada));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar region con HATEOAS", description = "Actualiza una region y retorna enlaces HATEOAS")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Region actualizada"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Region no encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EntityModel<RegionDTO>> update(@PathVariable Integer id,
                                                         @Valid @RequestBody RegionRequestDTO requestDTO) {
        log.info("Solicitud V2 para actualizar region con id: {}", id);
        RegionDTO regionActualizada = regionService.update(id, requestDTO);
        return ResponseEntity.ok(regionModelAssembler.toModel(regionActualizada));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar region", description = "Elimina una region por su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Region eliminada"),
            @ApiResponse(responseCode = "404", description = "Region no encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        log.info("Solicitud V2 para eliminar region con id: {}", id);
        regionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
