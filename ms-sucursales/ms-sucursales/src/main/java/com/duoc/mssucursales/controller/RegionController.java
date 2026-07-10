package com.duoc.mssucursales.controller;

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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Slf4j
@Tag(name = "Regiones", description = "Operaciones CRUD de regiones")
public class RegionController {

    private final RegionService regionService;

    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping("/regiones")
    @Operation(summary = "Listar regiones", description = "Retorna todas las regiones registradas")
    @ApiResponse(responseCode = "200", description = "Regiones encontradas")
    public ResponseEntity<List<RegionDTO>> findAll() {
        log.info("Solicitud para listar regiones");
        return ResponseEntity.ok(regionService.findAll());
    }

    @GetMapping("/regiones/{id}")
    @Operation(summary = "Buscar region por ID", description = "Retorna una region segun su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Region encontrada",
                    content = @Content(schema = @Schema(implementation = RegionDTO.class))),
            @ApiResponse(responseCode = "404", description = "Region no encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<RegionDTO> findById(@PathVariable Integer id) {
        log.info("Solicitud para buscar region con id: {}", id);
        return ResponseEntity.ok(regionService.findById(id));
    }

    @PostMapping("/regiones")
    @Operation(summary = "Crear region", description = "Registra una nueva region")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Region creada",
                    content = @Content(schema = @Schema(implementation = RegionDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos invalidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<RegionDTO> save(@Valid @RequestBody RegionRequestDTO requestDTO) {
        log.info("Solicitud para crear region: {}", requestDTO.getNombre());
        RegionDTO regionCreada = regionService.save(requestDTO);
        return ResponseEntity.status(201).body(regionCreada);
    }

    @PutMapping("/regiones/{id}")
    @Operation(summary = "Actualizar region", description = "Actualiza los datos de una region existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Region actualizada",
                    content = @Content(schema = @Schema(implementation = RegionDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos invalidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Region no encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<RegionDTO> update(@PathVariable Integer id,
                                            @Valid @RequestBody RegionRequestDTO requestDTO) {
        log.info("Solicitud para actualizar region con id: {}", id);
        return ResponseEntity.ok(regionService.update(id, requestDTO));
    }

    @DeleteMapping("/regiones/{id}")
    @Operation(summary = "Eliminar region", description = "Elimina una region segun su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Region eliminada"),
            @ApiResponse(responseCode = "404", description = "Region no encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        log.info("Solicitud para eliminar region con id: {}", id);
        regionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
