package com.duoc.mssucursales.controller;

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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Slf4j
@Tag(name = "Sucursales", description = "Operaciones CRUD y consultas de sucursales")
public class SucursalController {

    private final SucursalService sucursalService;

    public SucursalController(SucursalService sucursalService) {
        this.sucursalService = sucursalService;
    }

    @GetMapping("/sucursales")
    @Operation(summary = "Listar sucursales", description = "Retorna todas las sucursales registradas")
    @ApiResponse(responseCode = "200", description = "Sucursales encontradas")
    public ResponseEntity<List<SucursalDTO>> findAll() {
        log.info("Solicitud para listar sucursales");
        return ResponseEntity.ok(sucursalService.findAll());
    }

    @GetMapping("/sucursales/{id}")
    @Operation(summary = "Buscar sucursal por ID", description = "Retorna una sucursal segun su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucursal encontrada",
                    content = @Content(schema = @Schema(implementation = SucursalDTO.class))),
            @ApiResponse(responseCode = "404", description = "Sucursal no encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<SucursalDTO> findById(@PathVariable Integer id) {
        log.info("Solicitud para buscar sucursal con id: {}", id);
        return ResponseEntity.ok(sucursalService.findById(id));
    }

    @PostMapping("/sucursales")
    @Operation(summary = "Crear sucursal", description = "Registra una nueva sucursal")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sucursal creada",
                    content = @Content(schema = @Schema(implementation = SucursalDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos invalidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Region no encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<SucursalDTO> save(@Valid @RequestBody SucursalRequestDTO requestDTO) {
        log.info("Solicitud para crear sucursal: {}", requestDTO.getNombre());
        SucursalDTO sucursalCreada = sucursalService.save(requestDTO);
        return ResponseEntity.status(201).body(sucursalCreada);
    }

    @PutMapping("/sucursales/{id}")
    @Operation(summary = "Actualizar sucursal", description = "Actualiza los datos de una sucursal existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucursal actualizada",
                    content = @Content(schema = @Schema(implementation = SucursalDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos invalidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Sucursal o region no encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<SucursalDTO> update(@PathVariable Integer id,
                                              @Valid @RequestBody SucursalRequestDTO requestDTO) {
        log.info("Solicitud para actualizar sucursal con id: {}", id);
        return ResponseEntity.ok(sucursalService.update(id, requestDTO));
    }

    @DeleteMapping("/sucursales/{id}")
    @Operation(summary = "Eliminar sucursal", description = "Elimina una sucursal segun su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Sucursal eliminada"),
            @ApiResponse(responseCode = "404", description = "Sucursal no encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        log.info("Solicitud para eliminar sucursal con id: {}", id);
        sucursalService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/sucursales/operativas")
    @Operation(summary = "Listar sucursales operativas", description = "Retorna las sucursales operativas ordenadas")
    @ApiResponse(responseCode = "200", description = "Sucursales operativas encontradas")
    public ResponseEntity<List<SucursalDTO>> listarOperativasOrdenadas() {
        log.info("Solicitud para listar sucursales operativas");
        return ResponseEntity.ok(sucursalService.listarOperativasOrdenadas());
    }
}
