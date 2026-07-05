package com.duoc.ms_reportes.controller;

import com.duoc.ms_reportes.dto.ReporteDTO;
import com.duoc.ms_reportes.dto.ReporteRequestDTO;
import com.duoc.ms_reportes.service.ReporteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteService reporteService;

    @GetMapping("/reportes")
    public ResponseEntity<List<ReporteDTO>> findAll() {
        return ResponseEntity.ok(reporteService.findAll());
    }

    @GetMapping("/reportes/{id}")
    public ResponseEntity<ReporteDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(reporteService.findById(id));
    }

    @PostMapping("/reportes")
    public ResponseEntity<ReporteDTO> save(@Valid @RequestBody ReporteRequestDTO requestDTO) {
        ReporteDTO reporteCreado = reporteService.save(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(reporteCreado);
    }

    @PutMapping("/reportes/{id}")
    public ResponseEntity<ReporteDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody ReporteRequestDTO requestDTO
    ) {
        return ResponseEntity.ok(reporteService.update(id, requestDTO));
    }

    @DeleteMapping("/reportes/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        reporteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reportes/reserva/{reservaId}")
    public ResponseEntity<List<ReporteDTO>> findByReservaId(@PathVariable Integer reservaId) {
        return ResponseEntity.ok(reporteService.findByReservaId(reservaId));
    }

    @GetMapping("/reportes/pago-confirmado")
    public ResponseEntity<List<ReporteDTO>> findByPagoConfirmado(@RequestParam boolean confirmado) {
        return ResponseEntity.ok(reporteService.findByPagoConfirmado(confirmado));
    }
}
