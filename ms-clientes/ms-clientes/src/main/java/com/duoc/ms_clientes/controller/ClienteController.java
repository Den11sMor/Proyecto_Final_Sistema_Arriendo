package com.duoc.ms_clientes.controller;

import com.duoc.ms_clientes.dto.ClienteDTO;
import com.duoc.ms_clientes.dto.ClienteRequestDTO;
import com.duoc.ms_clientes.service.ClienteService;
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
@Tag(name = "Clientes", description = "Operaciones CRUD y búsqueda de clientes")
public class ClienteController {

    private final ClienteService clienteService;
    // GET /api/v1/clientes -> List<ClienteDTO> 200
    @GetMapping("/clientes")
    @Operation(summary = "Listar clientes")
    public ResponseEntity<List<ClienteDTO>> findAll() {
        return ResponseEntity.ok(clienteService.findAll());
    }
    // GET /api/v1/clientes/{id} -> ClienteDTO 200
    @GetMapping("/clientes/{id}")
    @Operation(summary = "Buscar cliente por ID")
    public ResponseEntity<ClienteDTO> findById (@PathVariable Integer id){
        return  ResponseEntity.ok(clienteService.findById(id));
    }
    // POST /api/v1/clientes -> ClienteRequestDTO -> ClienteDTO creado 201
    @PostMapping("/clientes")
    @Operation(summary = "Crear cliente")
    public ResponseEntity<ClienteDTO> save(@Valid @RequestBody ClienteRequestDTO request){
        ClienteDTO clienteCreado = clienteService.save(request);
        return  ResponseEntity.status(HttpStatus.CREATED).body(clienteCreado);
    }
    // PUT /api/v1/clientes/{id} -> id + ClienteRequestDTO -> ClienteDTO actualizado 200
    @PutMapping("/clientes/{id}")
    @Operation(summary = "Actualizar cliente")
    public ResponseEntity<ClienteDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody ClienteRequestDTO request){
        ClienteDTO clienteActualizado = clienteService.update(id, request);
        return ResponseEntity.ok(clienteActualizado);

    }


    // DELETE /api/v1/clientes/{id} -> vacío 204
    @DeleteMapping("/clientes/{id}")
    @Operation(summary = "Eliminar cliente")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // CAMBIO: endpoint para probar la query method requerida por la pauta
    @GetMapping("/clientes/buscar-email")
    @Operation(summary = "Buscar clientes por correo electrónico")
    public ResponseEntity<List<ClienteDTO>> buscarPorEmail(@RequestParam String texto){
        return ResponseEntity.ok(clienteService.buscarPorEmail(texto));
    }
}
