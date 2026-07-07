package com.duoc.mssucursales.controller;

import com.duoc.mssucursales.assemblers.RegionModelAssembler;
import com.duoc.mssucursales.dto.RegionDTO;
import com.duoc.mssucursales.dto.RegionRequestDTO;
import com.duoc.mssucursales.service.RegionService;
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
@RequestMapping("/api/v2/regiones")
@Tag(name = "Regiones V2", description = "Operaciones de regiones con HATEOAS")
public class RegionControllerV2 {

    private final RegionService regionService;
    private final RegionModelAssembler regionModelAssembler;

    @GetMapping
    @Operation(summary = "Listar regiones con HATEOAS")
    public ResponseEntity<CollectionModel<EntityModel<RegionDTO>>> findAll() {
        List<EntityModel<RegionDTO>> regiones = regionService.findAll()
                .stream()
                .map(regionModelAssembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(
                regiones,
                linkTo(methodOn(RegionControllerV2.class).findAll()).withSelfRel()
        ));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar region por ID con HATEOAS")
    public ResponseEntity<EntityModel<RegionDTO>> findById(@PathVariable Integer id) {
        RegionDTO region = regionService.findById(id);
        return ResponseEntity.ok(regionModelAssembler.toModel(region));
    }

    @PostMapping
    @Operation(summary = "Crear region con HATEOAS")
    public ResponseEntity<EntityModel<RegionDTO>> save(@Valid @RequestBody RegionRequestDTO requestDTO) {
        RegionDTO regionCreada = regionService.save(requestDTO);

        return ResponseEntity
                .created(linkTo(methodOn(RegionControllerV2.class).findById(regionCreada.getId())).toUri())
                .body(regionModelAssembler.toModel(regionCreada));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar region con HATEOAS")
    public ResponseEntity<EntityModel<RegionDTO>> update(@PathVariable Integer id,
                                                         @Valid @RequestBody RegionRequestDTO requestDTO) {
        RegionDTO regionActualizada = regionService.update(id, requestDTO);
        return ResponseEntity.ok(regionModelAssembler.toModel(regionActualizada));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar region")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        regionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}