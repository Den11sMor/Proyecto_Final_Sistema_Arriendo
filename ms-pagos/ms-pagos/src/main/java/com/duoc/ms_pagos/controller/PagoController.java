package com.duoc.ms_pagos.controller;

import com.duoc.ms_pagos.dto.PagoDTO;
import com.duoc.ms_pagos.dto.PagoRequestDTO;
import com.duoc.ms_pagos.service.PagoService;
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
public class PagoController {

    private final PagoService pagoService;

    @GetMapping("/pagos")
    public ResponseEntity<List<PagoDTO>> findAll() {
        return ResponseEntity.ok(pagoService.findAll());
    }

    @GetMapping("/pagos/{id}")
    public ResponseEntity<PagoDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(pagoService.findById(id));
    }

    @PostMapping("/pagos")
    public ResponseEntity<PagoDTO> save(@Valid @RequestBody PagoRequestDTO requestDTO) {
        PagoDTO pagoCreado = pagoService.save(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(pagoCreado);
    }

    @PutMapping("/pagos/{id}")
    public ResponseEntity<PagoDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody PagoRequestDTO requestDTO
    ) {
        return ResponseEntity.ok(pagoService.update(id, requestDTO));
    }

    @DeleteMapping("/pagos/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        pagoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // agregado: endpoint para probar la query JPQL por rango de monto
    @GetMapping("/pagos/rango")
    public ResponseEntity<List<PagoDTO>> buscarPorRangoMonto(
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max
    ) {
        return ResponseEntity.ok(pagoService.buscarPorRangoMonto(min, max));
    }
}
