package com.duoc.mssucursales.controller;

import com.duoc.mssucursales.assemblers.SucursalModelAssembler;
import com.duoc.mssucursales.dto.SucursalDTO;
import com.duoc.mssucursales.dto.SucursalRequestDTO;
import com.duoc.mssucursales.service.SucursalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/api/v2/sucursales")
@Tag(name = "Sucursales V2", description = "Operaciones de sucursales con HATEOAS")
public class SucursalControllerV2 {

    private final SucursalService sucursalService;
    private final SucursalModelAssembler sucursalModelAssembler;

    @GetMapping
    @Operation(summary = "Listar sucursales con HATEOAS")
    public ResponseEntity<CollectionModel<EntityModel<SucursalDTO>>> findAll() {
        List<EntityModel<SucursalDTO>> sucursales = sucursalService.findAll()
                .stream()
                .map(sucursalModelAssembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(
                sucursales,
                linkTo(methodOn(SucursalControllerV2.class).findAll()).withSelfRel()
        ));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar sucursal por ID con HATEOAS")
    public ResponseEntity<EntityModel<SucursalDTO>> findById(@PathVariable Integer id) {
        SucursalDTO sucursal = sucursalService.findById(id);
        return ResponseEntity.ok(sucursalModelAssembler.toModel(sucursal));
    }

    @PostMapping
    @Operation(summary = "Crear sucursal con HATEOAS")
    public ResponseEntity<EntityModel<SucursalDTO>> save(@Valid @RequestBody SucursalRequestDTO requestDTO) {
        SucursalDTO sucursalCreada = sucursalService.save(requestDTO);

        return ResponseEntity
                .created(linkTo(methodOn(SucursalControllerV2.class).findById(sucursalCreada.getId())).toUri())
                .body(sucursalModelAssembler.toModel(sucursalCreada));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar sucursal con HATEOAS")
    public ResponseEntity<EntityModel<SucursalDTO>> update(@PathVariable Integer id,
                                                           @Valid @RequestBody SucursalRequestDTO requestDTO) {
        SucursalDTO sucursalActualizada = sucursalService.update(id, requestDTO);
        return ResponseEntity.ok(sucursalModelAssembler.toModel(sucursalActualizada));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar sucursal")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        sucursalService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/operativas")
    @Operation(summary = "Listar sucursales operativas con HATEOAS")
    public ResponseEntity<CollectionModel<EntityModel<SucursalDTO>>> listarOperativasOrdenadas() {
        List<EntityModel<SucursalDTO>> sucursales = sucursalService.listarOperativasOrdenadas()
                .stream()
                .map(sucursalModelAssembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(
                sucursales,
                linkTo(methodOn(SucursalControllerV2.class).listarOperativasOrdenadas()).withSelfRel(),
                linkTo(methodOn(SucursalControllerV2.class).findAll()).withRel("sucursales")
        ));
    }
}