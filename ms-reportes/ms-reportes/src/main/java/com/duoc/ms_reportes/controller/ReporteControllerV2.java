package com.duoc.ms_reportes.controller;

import com.duoc.ms_reportes.assemblers.ReporteModelAssembler;
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
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2")
@Slf4j
@Tag(name = "Reportes V2", description = "Operaciones de reportes con respuestas HATEOAS")
public class ReporteControllerV2 {

    private final ReporteService reporteService;
    private final ReporteModelAssembler reporteModelAssembler;

    @GetMapping("/reportes")
    @Operation(summary = "Listar reportes con HATEOAS", description = "Retorna todos los reportes con enlaces relacionados")
    @ApiResponse(responseCode = "200", description = "Reportes encontrados")
    public ResponseEntity<CollectionModel<EntityModel<ReporteDTO>>> findAll() {
        log.info("Solicitud V2 para listar reportes");
        List<EntityModel<ReporteDTO>> reportes = reporteService.findAll()
                .stream()
                .map(reporteModelAssembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(
                reportes,
                Link.of("/api/v2/reportes").withSelfRel()
        ));
    }

    @GetMapping("/reportes/{id}")
    @Operation(summary = "Buscar reporte por ID con HATEOAS", description = "Retorna un reporte con enlaces relacionados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte encontrado"),
            @ApiResponse(responseCode = "404", description = "Reporte no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EntityModel<ReporteDTO>> findById(@PathVariable Integer id) {
        log.info("Solicitud V2 para buscar reporte con id: {}", id);
        ReporteDTO reporte = reporteService.findById(id);
        return ResponseEntity.ok(reporteModelAssembler.toModel(reporte));
    }

    @PostMapping("/reportes")
    @Operation(summary = "Crear reporte con HATEOAS", description = "Registra un nuevo reporte y retorna enlaces relacionados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reporte creado"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Reserva o pago no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EntityModel<ReporteDTO>> save(@Valid @RequestBody ReporteRequestDTO requestDTO) {
        log.info("Solicitud V2 para crear reporte de reserva {} y pago {}", requestDTO.getReservaId(), requestDTO.getPagoId());
        ReporteDTO reporteCreado = reporteService.save(requestDTO);

        return ResponseEntity
                .created(URI.create("/api/v2/reportes/" + reporteCreado.getId()))
                .body(reporteModelAssembler.toModel(reporteCreado));
    }

    @PutMapping("/reportes/{id}")
    @Operation(summary = "Actualizar reporte con HATEOAS", description = "Actualiza un reporte existente y retorna enlaces relacionados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte actualizado"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Reporte, reserva o pago no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EntityModel<ReporteDTO>> update(@PathVariable Integer id,
                                                          @Valid @RequestBody ReporteRequestDTO requestDTO) {
        log.info("Solicitud V2 para actualizar reporte con id: {}", id);
        ReporteDTO reporteActualizado = reporteService.update(id, requestDTO);
        return ResponseEntity.ok(reporteModelAssembler.toModel(reporteActualizado));
    }

    @DeleteMapping("/reportes/{id}")
    @Operation(summary = "Eliminar reporte", description = "Elimina un reporte segun su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Reporte eliminado"),
            @ApiResponse(responseCode = "404", description = "Reporte no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        log.info("Solicitud V2 para eliminar reporte con id: {}", id);
        reporteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reportes/reserva/{reservaId}")
    @Operation(summary = "Buscar reportes por reserva con HATEOAS", description = "Retorna reportes asociados a una reserva con enlaces relacionados")
    @ApiResponse(responseCode = "200", description = "Reportes encontrados")
    public ResponseEntity<CollectionModel<EntityModel<ReporteDTO>>> findByReservaId(@PathVariable Integer reservaId) {
        log.info("Solicitud V2 para buscar reportes por reserva id: {}", reservaId);
        List<EntityModel<ReporteDTO>> reportes = reporteService.findByReservaId(reservaId)
                .stream()
                .map(reporteModelAssembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(
                reportes,
                Link.of("/api/v2/reportes/reserva/" + reservaId).withSelfRel(),
                Link.of("/api/v2/reportes").withRel("reportes")
        ));
    }

    @GetMapping("/reportes/pago-confirmado")
    @Operation(summary = "Buscar reportes por estado de pago con HATEOAS", description = "Retorna reportes filtrados por pago confirmado con enlaces relacionados")
    @ApiResponse(responseCode = "200", description = "Reportes encontrados")
    public ResponseEntity<CollectionModel<EntityModel<ReporteDTO>>> findByPagoConfirmado(@RequestParam boolean confirmado) {
        log.info("Solicitud V2 para buscar reportes con pago confirmado: {}", confirmado);
        List<EntityModel<ReporteDTO>> reportes = reporteService.findByPagoConfirmado(confirmado)
                .stream()
                .map(reporteModelAssembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(
                reportes,
                Link.of("/api/v2/reportes/pago-confirmado?confirmado=" + confirmado).withSelfRel(),
                Link.of("/api/v2/reportes").withRel("reportes")
        ));
    }
}
