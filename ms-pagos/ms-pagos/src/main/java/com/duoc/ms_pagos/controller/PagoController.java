package com.duoc.ms_pagos.controller;

import com.duoc.ms_pagos.dto.PagoDTO;
import com.duoc.ms_pagos.dto.PagoRequestDTO;
import com.duoc.ms_pagos.service.PagoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Pagos", description = "Operaciones CRUD y consultas de pagos")
public class PagoController {

    private final PagoService pagoService;

    @GetMapping("/pagos")
    @Operation(summary = "Listar pagos", description = "Retorna todos los pagos registrados")
    public ResponseEntity<List<PagoDTO>> findAll() {
        return ResponseEntity.ok(pagoService.findAll());
    }

    @GetMapping("/pagos/{id}")
    @Operation(summary = "Buscar pago por ID", description = "Retorna un pago segun su identificador")
    public ResponseEntity<PagoDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(pagoService.findById(id));
    }

    @PostMapping("/pagos")
    @Operation(summary = "Crear pago", description = "Registra un nuevo pago")
    public ResponseEntity<PagoDTO> save(@Valid @RequestBody PagoRequestDTO requestDTO) {
        PagoDTO pagoCreado = pagoService.save(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(pagoCreado);
    }

    @PutMapping("/pagos/{id}")
    @Operation(summary = "Actualizar pago", description = "Actualiza los datos de un pago existente")
    public ResponseEntity<PagoDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody PagoRequestDTO requestDTO
    ) {
        return ResponseEntity.ok(pagoService.update(id, requestDTO));
    }

    @DeleteMapping("/pagos/{id}")
    @Operation(summary = "Eliminar pago", description = "Elimina un pago segun su identificador")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        pagoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pagos/rango")
    @Operation(summary = "Buscar pagos por rango de monto", description = "Retorna pagos filtrados por monto minimo y maximo")
    public ResponseEntity<List<PagoDTO>> buscarPorRangoMonto(
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max
    ) {
        return ResponseEntity.ok(pagoService.buscarPorRangoMonto(min, max));
    }
}