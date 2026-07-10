package com.duoc.ms_pagos.controller;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Pagos", description = "Operaciones CRUD y consultas de pagos")
public class PagoController {

    private final PagoService pagoService;

    @GetMapping("/pagos")
    @Operation(summary = "Listar pagos", description = "Retorna todos los pagos registrados")
    @ApiResponse(responseCode = "200", description = "Pagos encontrados")
    public ResponseEntity<List<PagoDTO>> findAll() {
        log.info("Solicitud para listar pagos");
        return ResponseEntity.ok(pagoService.findAll());
    }

    @GetMapping("/pagos/{id}")
    @Operation(summary = "Buscar pago por ID", description = "Retorna un pago segun su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago encontrado",
                    content = @Content(schema = @Schema(implementation = PagoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<PagoDTO> findById(@PathVariable Integer id) {
        log.info("Solicitud para buscar pago con id: {}", id);
        return ResponseEntity.ok(pagoService.findById(id));
    }

    @PostMapping("/pagos")
    @Operation(summary = "Crear pago", description = "Registra un nuevo pago")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pago creado",
                    content = @Content(schema = @Schema(implementation = PagoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos invalidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<PagoDTO> save(@Valid @RequestBody PagoRequestDTO requestDTO) {
        log.info("Solicitud para crear pago de reserva id: {}", requestDTO.getReservaId());
        PagoDTO pagoCreado = pagoService.save(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(pagoCreado);
    }

    @PutMapping("/pagos/{id}")
    @Operation(summary = "Actualizar pago", description = "Actualiza los datos de un pago existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago actualizado",
                    content = @Content(schema = @Schema(implementation = PagoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos invalidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Pago o reserva no encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<PagoDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody PagoRequestDTO requestDTO
    ) {
        log.info("Solicitud para actualizar pago con id: {}", id);
        return ResponseEntity.ok(pagoService.update(id, requestDTO));
    }

    @DeleteMapping("/pagos/{id}")
    @Operation(summary = "Eliminar pago", description = "Elimina un pago segun su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pago eliminado"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        log.info("Solicitud para eliminar pago con id: {}", id);
        pagoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pagos/rango")
    @Operation(summary = "Buscar pagos por rango de monto", description = "Retorna pagos filtrados por monto minimo y maximo")
    @ApiResponse(responseCode = "200", description = "Pagos encontrados")
    public ResponseEntity<List<PagoDTO>> buscarPorRangoMonto(
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max
    ) {
        log.info("Solicitud para buscar pagos entre montos {} y {}", min, max);
        return ResponseEntity.ok(pagoService.buscarPorRangoMonto(min, max));
    }
}
