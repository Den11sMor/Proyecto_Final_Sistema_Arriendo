package com.duoc.ms_reportes.controller;

import com.duoc.ms_reportes.dto.ReporteDTO;
import com.duoc.ms_reportes.dto.ReporteRequestDTO;
import com.duoc.ms_reportes.service.ReporteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Reportes", description = "Operaciones CRUD y consultas de reportes")
public class ReporteController {

    private final ReporteService reporteService;

    @GetMapping("/reportes")
    @Operation(summary = "Listar reportes", description = "Retorna todos los reportes registrados")
    public ResponseEntity<List<ReporteDTO>> findAll() {
        return ResponseEntity.ok(reporteService.findAll());
    }

    @GetMapping("/reportes/{id}")
    @Operation(summary = "Buscar reporte por ID", description = "Retorna un reporte segun su identificador")
    public ResponseEntity<ReporteDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(reporteService.findById(id));
    }

    @PostMapping("/reportes")
    @Operation(summary = "Crear reporte", description = "Registra un nuevo reporte")
    public ResponseEntity<ReporteDTO> save(@Valid @RequestBody ReporteRequestDTO requestDTO) {
        ReporteDTO reporteCreado = reporteService.save(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(reporteCreado);
    }

    @PutMapping("/reportes/{id}")
    @Operation(summary = "Actualizar reporte", description = "Actualiza los datos de un reporte existente")
    public ResponseEntity<ReporteDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody ReporteRequestDTO requestDTO
    ) {
        return ResponseEntity.ok(reporteService.update(id, requestDTO));
    }

    @DeleteMapping("/reportes/{id}")
    @Operation(summary = "Eliminar reporte", description = "Elimina un reporte segun su identificador")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        reporteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reportes/reserva/{reservaId}")
    @Operation(summary = "Buscar reportes por reserva", description = "Retorna reportes asociados a una reserva")
    public ResponseEntity<List<ReporteDTO>> findByReservaId(@PathVariable Integer reservaId) {
        return ResponseEntity.ok(reporteService.findByReservaId(reservaId));
    }

    @GetMapping("/reportes/pago-confirmado")
    @Operation(summary = "Buscar reportes por estado de pago", description = "Retorna reportes filtrados por pago confirmado")
    public ResponseEntity<List<ReporteDTO>> findByPagoConfirmado(@RequestParam boolean confirmado) {
        return ResponseEntity.ok(reporteService.findByPagoConfirmado(confirmado));
    }
}