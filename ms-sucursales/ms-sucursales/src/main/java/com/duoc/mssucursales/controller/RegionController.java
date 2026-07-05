package com.duoc.mssucursales.controller;

import com.duoc.mssucursales.dto.RegionDTO;
import com.duoc.mssucursales.dto.RegionRequestDTO;
import com.duoc.mssucursales.service.RegionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RegionController {

    private final RegionService regionService;

    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping("/regiones")
    public ResponseEntity<List<RegionDTO>> findAll() {
        return ResponseEntity.ok(regionService.findAll());
    }

    @GetMapping("/regiones/{id}")
    public ResponseEntity<RegionDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(regionService.findById(id));
    }

    @PostMapping("/regiones")
    public ResponseEntity<RegionDTO> save(@Valid @RequestBody RegionRequestDTO requestDTO) {
        RegionDTO regionCreada = regionService.save(requestDTO);
        return ResponseEntity.status(201).body(regionCreada);
    }

    @PutMapping("/regiones/{id}")
    public ResponseEntity<RegionDTO> update(@PathVariable Integer id,
                                            @Valid @RequestBody RegionRequestDTO requestDTO) {
        return ResponseEntity.ok(regionService.update(id, requestDTO));
    }

    @DeleteMapping("/regiones/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        regionService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
