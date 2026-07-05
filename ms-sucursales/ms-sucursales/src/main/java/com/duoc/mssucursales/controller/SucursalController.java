package com.duoc.mssucursales.controller;

import com.duoc.mssucursales.dto.SucursalDTO;
import com.duoc.mssucursales.dto.SucursalRequestDTO;
import com.duoc.mssucursales.service.SucursalService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class SucursalController {

    private final SucursalService sucursalService;

    public SucursalController(SucursalService sucursalService) {
        this.sucursalService = sucursalService;
    }

    @GetMapping("/sucursales")
    public ResponseEntity<List<SucursalDTO>> findAll() {
        return ResponseEntity.ok(sucursalService.findAll());
    }

    @GetMapping("/sucursales/{id}")
    public ResponseEntity<SucursalDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(sucursalService.findById(id));
    }

    @PostMapping("/sucursales")
    public ResponseEntity<SucursalDTO> save(@Valid @RequestBody SucursalRequestDTO requestDTO) {
        SucursalDTO sucursalCreada = sucursalService.save(requestDTO);
        return ResponseEntity.status(201).body(sucursalCreada);
    }

    @PutMapping("/sucursales/{id}")
    public ResponseEntity<SucursalDTO> update(@PathVariable Integer id,
                                              @Valid @RequestBody SucursalRequestDTO requestDTO) {
        return ResponseEntity.ok(sucursalService.update(id, requestDTO));
    }

    @DeleteMapping("/sucursales/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        sucursalService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/sucursales/operativas")
    public ResponseEntity<List<SucursalDTO>> listarOperativasOrdenadas() {
        return ResponseEntity.ok(sucursalService.listarOperativasOrdenadas());
    }

}
