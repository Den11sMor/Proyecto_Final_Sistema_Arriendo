package com.duoc.mssucursales.controller;

import com.duoc.mssucursales.dto.SucursalDTO;
import com.duoc.mssucursales.dto.SucursalRequestDTO;
import com.duoc.mssucursales.service.SucursalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Sucursales", description = "Operaciones CRUD y consultas de sucursales")
public class SucursalController {

    private final SucursalService sucursalService;

    public SucursalController(SucursalService sucursalService) {
        this.sucursalService = sucursalService;
    }

    @GetMapping("/sucursales")
    @Operation(summary = "Listar sucursales", description = "Retorna todas las sucursales registradas")
    public ResponseEntity<List<SucursalDTO>> findAll() {
        return ResponseEntity.ok(sucursalService.findAll());
    }

    @GetMapping("/sucursales/{id}")
    @Operation(summary = "Buscar sucursal por ID", description = "Retorna una sucursal segun su identificador")
    public ResponseEntity<SucursalDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(sucursalService.findById(id));
    }

    @PostMapping("/sucursales")
    @Operation(summary = "Crear sucursal", description = "Registra una nueva sucursal")
    public ResponseEntity<SucursalDTO> save(@Valid @RequestBody SucursalRequestDTO requestDTO) {
        SucursalDTO sucursalCreada = sucursalService.save(requestDTO);
        return ResponseEntity.status(201).body(sucursalCreada);
    }

    @PutMapping("/sucursales/{id}")
    @Operation(summary = "Actualizar sucursal", description = "Actualiza los datos de una sucursal existente")
    public ResponseEntity<SucursalDTO> update(@PathVariable Integer id,
                                              @Valid @RequestBody SucursalRequestDTO requestDTO) {
        return ResponseEntity.ok(sucursalService.update(id, requestDTO));
    }

    @DeleteMapping("/sucursales/{id}")
    @Operation(summary = "Eliminar sucursal", description = "Elimina una sucursal segun su identificador")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        sucursalService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/sucursales/operativas")
    @Operation(summary = "Listar sucursales operativas", description = "Retorna las sucursales operativas ordenadas")
    public ResponseEntity<List<SucursalDTO>> listarOperativasOrdenadas() {
        return ResponseEntity.ok(sucursalService.listarOperativasOrdenadas());
    }
}