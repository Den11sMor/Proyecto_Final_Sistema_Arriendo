package com.duoc.ms_clientes.controller;

import com.duoc.ms_clientes.dto.DireccionDTO;
import com.duoc.ms_clientes.dto.DireccionRequestDTO;
import com.duoc.ms_clientes.service.ClienteService;
import com.duoc.ms_clientes.service.DireccionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Direcciones", description = "Operaciones CRUD de direcciones de clientes")
public class DireccionController {

    private final DireccionService direccionService;

    // GET /api/v1/direcciones -> List<DireccionDTO> 200
    @GetMapping("/direcciones")
    @Operation(summary = "Listar direcciones")
    public ResponseEntity<List<DireccionDTO>> findAll() {
        return ResponseEntity.ok(direccionService.findAll());
    }

    // GET /api/v1/direcciones/{id} -> DireccionDTO 200
    @GetMapping("/direcciones/{id}")
    @Operation(summary = "Buscar dirección por ID")
    public ResponseEntity<DireccionDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(direccionService.findById(id));
    }

    // POST /api/v1/direcciones -> DireccionRequestDTO -> DireccionDTO creado 201
    @PostMapping("/direcciones")
    @Operation(summary = "Crear dirección")
    public ResponseEntity<DireccionDTO> save(@Valid @RequestBody DireccionRequestDTO request) {
        DireccionDTO direccionCreada = direccionService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(direccionCreada);
    }

    // PUT /api/v1/direcciones/{id} -> id + DireccionRequestDTO -> DireccionDTO actualizado 200
    @PutMapping("/direcciones/{id}")
    @Operation(summary = "Actualizar dirección")
    public ResponseEntity<DireccionDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody DireccionRequestDTO request) {

        DireccionDTO direccionActualizada = direccionService.update(id, request);
        return ResponseEntity.ok(direccionActualizada);
    }

    // DELETE /api/v1/direcciones/{id} -> vacío 204
    @DeleteMapping("/direcciones/{id}")
    @Operation(summary = "Eliminar dirección")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        direccionService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
