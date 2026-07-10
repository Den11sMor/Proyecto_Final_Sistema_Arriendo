package com.duoc.ms_reportes.controller;

import com.duoc.ms_reportes.dto.ReporteDTO;
import com.duoc.ms_reportes.dto.ReporteRequestDTO;
import com.duoc.ms_reportes.exception.ErrorResponse;
import com.duoc.ms_reportes.service.ReporteService;
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

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Reportes", description = "Operaciones CRUD y consultas de reportes")
public class ReporteController {

    private final ReporteService reporteService;

    @GetMapping("/reportes")
    @Operation(summary = "Listar reportes", description = "Retorna todos los reportes registrados")
    @ApiResponse(responseCode = "200", description = "Reportes encontrados")
    public ResponseEntity<List<ReporteDTO>> findAll() {
        log.info("Solicitud para listar reportes");
        return ResponseEntity.ok(reporteService.findAll());
    }

    @GetMapping("/reportes/{id}")
    @Operation(summary = "Buscar reporte por ID", description = "Retorna un reporte segun su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte encontrado",
                    content = @Content(schema = @Schema(implementation = ReporteDTO.class))),
            @ApiResponse(responseCode = "404", description = "Reporte no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<ReporteDTO> findById(@PathVariable Integer id) {
        log.info("Solicitud para buscar reporte con id: {}", id);
        return ResponseEntity.ok(reporteService.findById(id));
    }

    @PostMapping("/reportes")
    @Operation(summary = "Crear reporte", description = "Registra un nuevo reporte")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reporte creado",
                    content = @Content(schema = @Schema(implementation = ReporteDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos invalidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Reserva o pago no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<ReporteDTO> save(@Valid @RequestBody ReporteRequestDTO requestDTO) {
        log.info("Solicitud para crear reporte de reserva {} y pago {}", requestDTO.getReservaId(), requestDTO.getPagoId());
        ReporteDTO reporteCreado = reporteService.save(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(reporteCreado);
    }

    @PutMapping("/reportes/{id}")
    @Operation(summary = "Actualizar reporte", description = "Actualiza los datos de un reporte existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte actualizado",
                    content = @Content(schema = @Schema(implementation = ReporteDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos invalidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Reporte, reserva o pago no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<ReporteDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody ReporteRequestDTO requestDTO
    ) {
        log.info("Solicitud para actualizar reporte con id: {}", id);
        return ResponseEntity.ok(reporteService.update(id, requestDTO));
    }

    @DeleteMapping("/reportes/{id}")
    @Operation(summary = "Eliminar reporte", description = "Elimina un reporte segun su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Reporte eliminado"),
            @ApiResponse(responseCode = "404", description = "Reporte no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        log.info("Solicitud para eliminar reporte con id: {}", id);
        reporteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reportes/reserva/{reservaId}")
    @Operation(summary = "Buscar reportes por reserva", description = "Retorna reportes asociados a una reserva")
    @ApiResponse(responseCode = "200", description = "Reportes encontrados")
    public ResponseEntity<List<ReporteDTO>> findByReservaId(@PathVariable Integer reservaId) {
        log.info("Solicitud para buscar reportes por reserva id: {}", reservaId);
        return ResponseEntity.ok(reporteService.findByReservaId(reservaId));
    }

    @GetMapping("/reportes/pago-confirmado")
    @Operation(summary = "Buscar reportes por estado de pago", description = "Retorna reportes filtrados por pago confirmado")
    @ApiResponse(responseCode = "200", description = "Reportes encontrados")
    public ResponseEntity<List<ReporteDTO>> findByPagoConfirmado(@RequestParam boolean confirmado) {
        log.info("Solicitud para buscar reportes con pago confirmado: {}", confirmado);
        return ResponseEntity.ok(reporteService.findByPagoConfirmado(confirmado));
    }
}
