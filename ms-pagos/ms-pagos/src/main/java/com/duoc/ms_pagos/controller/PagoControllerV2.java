package com.duoc.ms_pagos.controller;

import com.duoc.ms_pagos.assemblers.PagoModelAssembler;
import com.duoc.ms_pagos.dto.PagoDTO;
import com.duoc.ms_pagos.dto.PagoRequestDTO;
import com.duoc.ms_pagos.exception.ErrorResponse;
import com.duoc.ms_pagos.service.PagoService;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2")
@Slf4j
@Tag(name = "Pagos V2", description = "Operaciones de pagos con respuestas HATEOAS")
public class PagoControllerV2 {

    private final PagoService pagoService;
    private final PagoModelAssembler pagoModelAssembler;

    @GetMapping("/pagos")
    @Operation(summary = "Listar pagos con HATEOAS", description = "Retorna todos los pagos con enlaces relacionados")
    @ApiResponse(responseCode = "200", description = "Pagos encontrados")
    public ResponseEntity<CollectionModel<EntityModel<PagoDTO>>> findAll() {
        log.info("Solicitud V2 para listar pagos");
        List<EntityModel<PagoDTO>> pagos = pagoService.findAll()
                .stream()
                .map(pagoModelAssembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(
                pagos,
                Link.of("/api/v2/pagos").withSelfRel()
        ));
    }

    @GetMapping("/pagos/{id}")
    @Operation(summary = "Buscar pago por ID con HATEOAS", description = "Retorna un pago con enlaces relacionados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago encontrado"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EntityModel<PagoDTO>> findById(@PathVariable Integer id) {
        log.info("Solicitud V2 para buscar pago con id: {}", id);
        PagoDTO pago = pagoService.findById(id);
        return ResponseEntity.ok(pagoModelAssembler.toModel(pago));
    }

    @PostMapping("/pagos")
    @Operation(summary = "Crear pago con HATEOAS", description = "Registra un nuevo pago y retorna enlaces relacionados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pago creado"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EntityModel<PagoDTO>> save(@Valid @RequestBody PagoRequestDTO requestDTO) {
        log.info("Solicitud V2 para crear pago de reserva id: {}", requestDTO.getReservaId());
        PagoDTO pagoCreado = pagoService.save(requestDTO);

        return ResponseEntity
                .created(URI.create("/api/v2/pagos/" + pagoCreado.getId()))
                .body(pagoModelAssembler.toModel(pagoCreado));
    }

    @PutMapping("/pagos/{id}")
    @Operation(summary = "Actualizar pago con HATEOAS", description = "Actualiza un pago existente y retorna enlaces relacionados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago actualizado"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Pago o reserva no encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EntityModel<PagoDTO>> update(@PathVariable Integer id,
                                                       @Valid @RequestBody PagoRequestDTO requestDTO) {
        log.info("Solicitud V2 para actualizar pago con id: {}", id);
        PagoDTO pagoActualizado = pagoService.update(id, requestDTO);
        return ResponseEntity.ok(pagoModelAssembler.toModel(pagoActualizado));
    }

    @DeleteMapping("/pagos/{id}")
    @Operation(summary = "Eliminar pago", description = "Elimina un pago segun su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pago eliminado"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        log.info("Solicitud V2 para eliminar pago con id: {}", id);
        pagoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pagos/rango")
    @Operation(summary = "Buscar pagos por rango de monto con HATEOAS", description = "Retorna pagos filtrados por monto minimo y maximo con enlaces relacionados")
    @ApiResponse(responseCode = "200", description = "Pagos encontrados")
    public ResponseEntity<CollectionModel<EntityModel<PagoDTO>>> buscarPorRangoMonto(
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max) {

        log.info("Solicitud V2 para buscar pagos entre montos {} y {}", min, max);
        List<EntityModel<PagoDTO>> pagos = pagoService.buscarPorRangoMonto(min, max)
                .stream()
                .map(pagoModelAssembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(
                pagos,
                Link.of("/api/v2/pagos/rango?min=" + min + "&max=" + max).withSelfRel(),
                Link.of("/api/v2/pagos").withRel("pagos")
        ));
    }
}
