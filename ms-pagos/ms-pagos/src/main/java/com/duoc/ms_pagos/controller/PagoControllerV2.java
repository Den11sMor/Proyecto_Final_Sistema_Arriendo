package com.duoc.ms_pagos.controller;

import com.duoc.ms_pagos.assemblers.PagoModelAssembler;
import com.duoc.ms_pagos.dto.PagoDTO;
import com.duoc.ms_pagos.dto.PagoRequestDTO;
import com.duoc.ms_pagos.service.PagoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2")
public class PagoControllerV2 {

    private final PagoService pagoService;
    private final PagoModelAssembler pagoModelAssembler;

    @GetMapping("/pagos")
    public ResponseEntity<CollectionModel<EntityModel<PagoDTO>>> findAll() {
        List<EntityModel<PagoDTO>> pagos = pagoService.findAll()
                .stream()
                .map(pagoModelAssembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(
                pagos,
                linkTo(methodOn(PagoControllerV2.class).findAll()).withSelfRel()
        ));
    }

    @GetMapping("/pagos/{id}")
    public ResponseEntity<EntityModel<PagoDTO>> findById(@PathVariable Integer id) {
        PagoDTO pago = pagoService.findById(id);
        return ResponseEntity.ok(pagoModelAssembler.toModel(pago));
    }

    @PostMapping("/pagos")
    public ResponseEntity<EntityModel<PagoDTO>> save(@Valid @RequestBody PagoRequestDTO requestDTO) {
        PagoDTO pagoCreado = pagoService.save(requestDTO);

        return ResponseEntity
                .created(linkTo(methodOn(PagoControllerV2.class).findById(pagoCreado.getId())).toUri())
                .body(pagoModelAssembler.toModel(pagoCreado));
    }

    @PutMapping("/pagos/{id}")
    public ResponseEntity<EntityModel<PagoDTO>> update(@PathVariable Integer id,
                                                       @Valid @RequestBody PagoRequestDTO requestDTO) {
        PagoDTO pagoActualizado = pagoService.update(id, requestDTO);
        return ResponseEntity.ok(pagoModelAssembler.toModel(pagoActualizado));
    }

    @DeleteMapping("/pagos/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        pagoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pagos/rango")
    public ResponseEntity<CollectionModel<EntityModel<PagoDTO>>> buscarPorRangoMonto(
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max) {

        List<EntityModel<PagoDTO>> pagos = pagoService.buscarPorRangoMonto(min, max)
                .stream()
                .map(pagoModelAssembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(
                pagos,
                linkTo(methodOn(PagoControllerV2.class).buscarPorRangoMonto(min, max)).withSelfRel(),
                linkTo(methodOn(PagoControllerV2.class).findAll()).withRel("pagos")
        ));
    }
}
