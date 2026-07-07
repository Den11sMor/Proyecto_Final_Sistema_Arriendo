package com.duoc.mssucursales.controller;

import com.duoc.mssucursales.dto.RegionDTO;
import com.duoc.mssucursales.dto.RegionRequestDTO;
import com.duoc.mssucursales.service.RegionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Regiones", description = "Operaciones CRUD de regiones")
public class RegionController {

    private final RegionService regionService;

    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping("/regiones")
    @Operation(summary = "Listar regiones", description = "Retorna todas las regiones registradas")
    public ResponseEntity<List<RegionDTO>> findAll() {
        return ResponseEntity.ok(regionService.findAll());
    }

    @GetMapping("/regiones/{id}")
    @Operation(summary = "Buscar region por ID", description = "Retorna una region segun su identificador")
    public ResponseEntity<RegionDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(regionService.findById(id));
    }

    @PostMapping("/regiones")
    @Operation(summary = "Crear region", description = "Registra una nueva region")
    public ResponseEntity<RegionDTO> save(@Valid @RequestBody RegionRequestDTO requestDTO) {
        RegionDTO regionCreada = regionService.save(requestDTO);
        return ResponseEntity.status(201).body(regionCreada);
    }

    @PutMapping("/regiones/{id}")
    @Operation(summary = "Actualizar region", description = "Actualiza los datos de una region existente")
    public ResponseEntity<RegionDTO> update(@PathVariable Integer id,
                                            @Valid @RequestBody RegionRequestDTO requestDTO) {
        return ResponseEntity.ok(regionService.update(id, requestDTO));
    }

    @DeleteMapping("/regiones/{id}")
    @Operation(summary = "Eliminar region", description = "Elimina una region segun su identificador")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        regionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}