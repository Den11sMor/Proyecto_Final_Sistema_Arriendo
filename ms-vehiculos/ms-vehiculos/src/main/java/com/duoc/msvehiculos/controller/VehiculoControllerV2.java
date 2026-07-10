package com.duoc.msvehiculos.controller;

import com.duoc.msvehiculos.assemblers.VehiculoModelAssembler;
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
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

/**
 * Controlador REST V2 para gestionar vehiculos con enlaces HATEOAS.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2")
@Slf4j
@Tag(name = "Vehiculos V2", description = "Operaciones de vehiculos con HATEOAS")
public class VehiculoControllerV2 {

    private final VehiculoService vehiculoService;
    private final VehiculoModelAssembler vehiculoModelAssembler;

    @GetMapping("/vehiculos")
    @Operation(summary = "Listar vehiculos con HATEOAS", description = "Obtiene todos los vehiculos con enlaces HATEOAS")
    @ApiResponse(responseCode = "200", description = "Vehiculos encontrados")
    public ResponseEntity<CollectionModel<EntityModel<VehiculoDTO>>> findAll() {
        log.info("Solicitud V2 para listar vehiculos");
        List<EntityModel<VehiculoDTO>> vehiculos = vehiculoService.findAll()
                .stream()
                .map(vehiculoModelAssembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(
                vehiculos,
                Link.of("/api/v2/vehiculos").withSelfRel()
        ));
    }

    @GetMapping("/vehiculos/{id}")
    @Operation(summary = "Buscar vehiculo por ID con HATEOAS", description = "Obtiene un vehiculo por su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehiculo encontrado"),
            @ApiResponse(responseCode = "404", description = "Vehiculo no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EntityModel<VehiculoDTO>> findById(@PathVariable Integer id) {
        log.info("Solicitud V2 para buscar vehiculo con id: {}", id);
        VehiculoDTO vehiculo = vehiculoService.findById(id);
        return ResponseEntity.ok(vehiculoModelAssembler.toModel(vehiculo));
    }

    @PostMapping("/vehiculos")
    @Operation(summary = "Crear vehiculo con HATEOAS", description = "Registra un vehiculo y retorna enlaces HATEOAS")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vehiculo creado"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EntityModel<VehiculoDTO>> save(@Valid @RequestBody VehiculoRequestDTO dto) {
        log.info("Solicitud V2 para crear vehiculo con patente: {}", dto.getPatente());
        VehiculoDTO vehiculoCreado = vehiculoService.save(dto);

        return ResponseEntity
                .created(URI.create("/api/v2/vehiculos/" + vehiculoCreado.getId()))
                .body(vehiculoModelAssembler.toModel(vehiculoCreado));
    }

    @PutMapping("/vehiculos/{id}")
    @Operation(summary = "Actualizar vehiculo con HATEOAS", description = "Actualiza un vehiculo por su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehiculo actualizado"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Vehiculo no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EntityModel<VehiculoDTO>> update(@PathVariable Integer id,
                                                           @Valid @RequestBody VehiculoRequestDTO dto) {
        log.info("Solicitud V2 para actualizar vehiculo con id: {}", id);
        VehiculoDTO vehiculoActualizado = vehiculoService.update(id, dto);
        return ResponseEntity.ok(vehiculoModelAssembler.toModel(vehiculoActualizado));
    }

    @DeleteMapping("/vehiculos/{id}")
    @Operation(summary = "Eliminar vehiculo", description = "Elimina un vehiculo por su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Vehiculo eliminado"),
            @ApiResponse(responseCode = "404", description = "Vehiculo no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        log.info("Solicitud V2 para eliminar vehiculo con id: {}", id);
        vehiculoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/vehiculos/disponibles/precio-menor/{precioMaximo}")
    @Operation(summary = "Buscar vehiculos disponibles por precio con HATEOAS",
            description = "Lista vehiculos disponibles con precio diario menor al valor indicado")
    @ApiResponse(responseCode = "200", description = "Vehiculos disponibles encontrados")
    public ResponseEntity<CollectionModel<EntityModel<VehiculoDTO>>> buscarDisponiblesPorPrecioMenor(
            @PathVariable BigDecimal precioMaximo) {

        log.info("Solicitud V2 para buscar vehiculos disponibles con precio menor a: {}", precioMaximo);
        List<EntityModel<VehiculoDTO>> vehiculos = vehiculoService.buscarDisponiblesPorPrecioMenor(precioMaximo)
                .stream()
                .map(vehiculoModelAssembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(
                vehiculos,
                Link.of("/api/v2/vehiculos/disponibles/precio-menor/" + precioMaximo).withSelfRel(),
                Link.of("/api/v2/vehiculos").withRel("vehiculos")
        ));
    }
}
