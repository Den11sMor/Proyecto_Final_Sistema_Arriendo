package com.duoc.msvehiculos.controller;

import com.duoc.msvehiculos.assemblers.VehiculoModelAssembler;
import com.duoc.msvehiculos.dto.VehiculoDTO;
import com.duoc.msvehiculos.dto.VehiculoRequestDTO;
import com.duoc.msvehiculos.service.VehiculoService;
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
public class VehiculoControllerV2 {

    private final VehiculoService vehiculoService;
    private final VehiculoModelAssembler vehiculoModelAssembler;

    @GetMapping("/vehiculos")
    public ResponseEntity<CollectionModel<EntityModel<VehiculoDTO>>> findAll() {
        List<EntityModel<VehiculoDTO>> vehiculos = vehiculoService.findAll()
                .stream()
                .map(vehiculoModelAssembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(
                vehiculos,
                linkTo(methodOn(VehiculoControllerV2.class).findAll()).withSelfRel()
        ));
    }

    @GetMapping("/vehiculos/{id}")
    public ResponseEntity<EntityModel<VehiculoDTO>> findById(@PathVariable Integer id) {
        VehiculoDTO vehiculo = vehiculoService.findById(id);
        return ResponseEntity.ok(vehiculoModelAssembler.toModel(vehiculo));
    }

    @PostMapping("/vehiculos")
    public ResponseEntity<EntityModel<VehiculoDTO>> save(@Valid @RequestBody VehiculoRequestDTO dto) {
        VehiculoDTO vehiculoCreado = vehiculoService.save(dto);

        return ResponseEntity
                .created(linkTo(methodOn(VehiculoControllerV2.class).findById(vehiculoCreado.getId())).toUri())
                .body(vehiculoModelAssembler.toModel(vehiculoCreado));
    }

    @PutMapping("/vehiculos/{id}")
    public ResponseEntity<EntityModel<VehiculoDTO>> update(@PathVariable Integer id,
                                                           @Valid @RequestBody VehiculoRequestDTO dto) {
        VehiculoDTO vehiculoActualizado = vehiculoService.update(id, dto);
        return ResponseEntity.ok(vehiculoModelAssembler.toModel(vehiculoActualizado));
    }

    @DeleteMapping("/vehiculos/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        vehiculoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/vehiculos/disponibles/precio-menor/{precioMaximo}")
    public ResponseEntity<CollectionModel<EntityModel<VehiculoDTO>>> buscarDisponiblesPorPrecioMenor(
            @PathVariable BigDecimal precioMaximo) {

        List<EntityModel<VehiculoDTO>> vehiculos = vehiculoService.buscarDisponiblesPorPrecioMenor(precioMaximo)
                .stream()
                .map(vehiculoModelAssembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(
                vehiculos,
                linkTo(methodOn(VehiculoControllerV2.class)
                        .buscarDisponiblesPorPrecioMenor(precioMaximo)).withSelfRel(),
                linkTo(methodOn(VehiculoControllerV2.class).findAll()).withRel("vehiculos")
        ));
    }
}