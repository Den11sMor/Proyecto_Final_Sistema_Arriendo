package com.duoc.msvehiculos.controller;

import com.duoc.msvehiculos.dto.VehiculoDTO;
import com.duoc.msvehiculos.dto.VehiculoRequestDTO;
import com.duoc.msvehiculos.exception.ErrorResponse;
import com.duoc.msvehiculos.service.VehiculoService;
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

import java.math.BigDecimal;
import java.util.List;

/**
 * Controlador REST V1 para gestionar vehiculos.
 */
@RestController
@RequestMapping("/api/v1/vehiculos")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Vehiculos V1", description = "Operaciones CRUD y consulta de disponibilidad de vehiculos")
public class VehiculoController {

    private final VehiculoService vehiculoService;

    @GetMapping
    @Operation(summary = "Listar vehiculos", description = "Obtiene todos los vehiculos registrados")
    @ApiResponse(responseCode = "200", description = "Vehiculos encontrados")
    public ResponseEntity<List<VehiculoDTO>> findAll() {
        log.info("Solicitud para listar vehiculos");
        return ResponseEntity.ok(vehiculoService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar vehiculo por ID", description = "Obtiene un vehiculo segun su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehiculo encontrado",
                    content = @Content(schema = @Schema(implementation = VehiculoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Vehiculo no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<VehiculoDTO> findById(@PathVariable Integer id) {
        log.info("Solicitud para buscar vehiculo con id: {}", id);
        return ResponseEntity.ok(vehiculoService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Crear vehiculo", description = "Registra un nuevo vehiculo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vehiculo creado",
                    content = @Content(schema = @Schema(implementation = VehiculoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos invalidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<VehiculoDTO> save(@Valid @RequestBody VehiculoRequestDTO dto) {
        log.info("Solicitud para crear vehiculo con patente: {}", dto.getPatente());
        VehiculoDTO vehiculoCreado = vehiculoService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(vehiculoCreado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar vehiculo", description = "Actualiza un vehiculo existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehiculo actualizado",
                    content = @Content(schema = @Schema(implementation = VehiculoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos invalidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Vehiculo no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<VehiculoDTO> update(@PathVariable Integer id,
                                              @Valid @RequestBody VehiculoRequestDTO dto) {
        log.info("Solicitud para actualizar vehiculo con id: {}", id);
        VehiculoDTO vehiculoActualizado = vehiculoService.update(id, dto);
        return ResponseEntity.ok(vehiculoActualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar vehiculo", description = "Elimina un vehiculo por su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Vehiculo eliminado"),
            @ApiResponse(responseCode = "404", description = "Vehiculo no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        log.info("Solicitud para eliminar vehiculo con id: {}", id);
        vehiculoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/disponibles/precio-menor/{precioMaximo}")
    @Operation(summary = "Buscar vehiculos disponibles por precio", description = "Lista vehiculos disponibles con precio diario menor al valor indicado")
    @ApiResponse(responseCode = "200", description = "Vehiculos disponibles encontrados")
    public ResponseEntity<List<VehiculoDTO>> buscarDisponiblesPorPrecioMenor(@PathVariable BigDecimal precioMaximo) {
        log.info("Solicitud para buscar vehiculos disponibles con precio menor a: {}", precioMaximo);
        return ResponseEntity.ok(vehiculoService.buscarDisponiblesPorPrecioMenor(precioMaximo));
    }
}
