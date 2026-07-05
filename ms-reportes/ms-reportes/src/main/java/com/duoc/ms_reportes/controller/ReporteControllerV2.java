package com.duoc.ms_reportes.controller;

import com.duoc.ms_reportes.assemblers.ReporteModelAssembler;
import com.duoc.ms_reportes.dto.ReporteDTO;
import com.duoc.ms_reportes.dto.ReporteRequestDTO;
import com.duoc.ms_reportes.service.ReporteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2")
public class ReporteControllerV2 {

    private final ReporteService reporteService;
    private final ReporteModelAssembler reporteModelAssembler;

    @GetMapping("/reportes")
    public ResponseEntity<CollectionModel<EntityModel<ReporteDTO>>> findAll() {
        List<EntityModel<ReporteDTO>> reportes = reporteService.findAll()
                .stream()
                .map(reporteModelAssembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(
                reportes,
                linkTo(methodOn(ReporteControllerV2.class).findAll()).withSelfRel()
        ));
    }

    @GetMapping("/reportes/{id}")
    public ResponseEntity<EntityModel<ReporteDTO>> findById(@PathVariable Integer id) {
        ReporteDTO reporte = reporteService.findById(id);
        return ResponseEntity.ok(reporteModelAssembler.toModel(reporte));
    }

    @PostMapping("/reportes")
    public ResponseEntity<EntityModel<ReporteDTO>> save(@Valid @RequestBody ReporteRequestDTO requestDTO) {
        ReporteDTO reporteCreado = reporteService.save(requestDTO);

        return ResponseEntity
                .created(linkTo(methodOn(ReporteControllerV2.class).findById(reporteCreado.getId())).toUri())
                .body(reporteModelAssembler.toModel(reporteCreado));
    }

    @PutMapping("/reportes/{id}")
    public ResponseEntity<EntityModel<ReporteDTO>> update(@PathVariable Integer id,
                                                          @Valid @RequestBody ReporteRequestDTO requestDTO) {
        ReporteDTO reporteActualizado = reporteService.update(id, requestDTO);
        return ResponseEntity.ok(reporteModelAssembler.toModel(reporteActualizado));
    }

    @DeleteMapping("/reportes/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        reporteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reportes/reserva/{reservaId}")
    public ResponseEntity<CollectionModel<EntityModel<ReporteDTO>>> findByReservaId(@PathVariable Integer reservaId) {
        List<EntityModel<ReporteDTO>> reportes = reporteService.findByReservaId(reservaId)
                .stream()
                .map(reporteModelAssembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(
                reportes,
                linkTo(methodOn(ReporteControllerV2.class).findByReservaId(reservaId)).withSelfRel(),
                linkTo(methodOn(ReporteControllerV2.class).findAll()).withRel("reportes")
        ));
    }

    @GetMapping("/reportes/pago-confirmado")
    public ResponseEntity<CollectionModel<EntityModel<ReporteDTO>>> findByPagoConfirmado(@RequestParam boolean confirmado) {
        List<EntityModel<ReporteDTO>> reportes = reporteService.findByPagoConfirmado(confirmado)
                .stream()
                .map(reporteModelAssembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(
                reportes,
                linkTo(methodOn(ReporteControllerV2.class).findByPagoConfirmado(confirmado)).withSelfRel(),
                linkTo(methodOn(ReporteControllerV2.class).findAll()).withRel("reportes")
        ));
    }
}
