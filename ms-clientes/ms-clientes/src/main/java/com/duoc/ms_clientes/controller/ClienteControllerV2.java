package com.duoc.ms_clientes.controller;

import com.duoc.ms_clientes.assemblers.ClienteModelAssembler;
import com.duoc.ms_clientes.dto.ClienteDTO;
import com.duoc.ms_clientes.dto.ClienteRequestDTO;
import com.duoc.ms_clientes.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("/api/v2")
@Tag(name = "Clientes V2", description = "Operaciones de clientes con respuestas HATEOAS")
public class ClienteControllerV2 {

    private final ClienteService clienteService;
    private final ClienteModelAssembler clienteModelAssembler;

    @GetMapping("/clientes")
    @Operation(summary = "Listar clientes con HATEOAS")
    public ResponseEntity<CollectionModel<EntityModel<ClienteDTO>>> findAll() {
        List<EntityModel<ClienteDTO>> clientes = clienteService.findAll()
                .stream()
                .map(clienteModelAssembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(
                clientes,
                linkTo(methodOn(ClienteControllerV2.class).findAll()).withSelfRel()
        ));
    }

    @GetMapping("/clientes/{id}")
    @Operation(summary = "Buscar cliente por ID con HATEOAS")
    public ResponseEntity<EntityModel<ClienteDTO>> findById(
            @Parameter(description = "ID del cliente", example = "1", required = true)
            @PathVariable Integer id) {
        ClienteDTO cliente = clienteService.findById(id);
        return ResponseEntity.ok(clienteModelAssembler.toModel(cliente));
    }

    @PostMapping("/clientes")
    @Operation(summary = "Crear cliente con HATEOAS")
    public ResponseEntity<EntityModel<ClienteDTO>> save(@Valid @RequestBody ClienteRequestDTO request) {
        ClienteDTO clienteCreado = clienteService.save(request);

        return ResponseEntity
                .created(linkTo(methodOn(ClienteControllerV2.class).findById(clienteCreado.getId())).toUri())
                .body(clienteModelAssembler.toModel(clienteCreado));
    }

    @PutMapping("/clientes/{id}")
    @Operation(summary = "Actualizar cliente con HATEOAS")
    public ResponseEntity<EntityModel<ClienteDTO>> update(
            @Parameter(description = "ID del cliente", example = "1", required = true)
            @PathVariable Integer id,
            @Valid @RequestBody ClienteRequestDTO request) {
        ClienteDTO clienteActualizado = clienteService.update(id, request);
        return ResponseEntity.ok(clienteModelAssembler.toModel(clienteActualizado));
    }

    @DeleteMapping("/clientes/{id}")
    @Operation(summary = "Eliminar cliente")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID del cliente", example = "1", required = true)
            @PathVariable Integer id) {
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/clientes/buscar-email")
    @Operation(summary = "Buscar clientes por email con HATEOAS")
    public ResponseEntity<CollectionModel<EntityModel<ClienteDTO>>> buscarPorEmail(
            @Parameter(description = "Texto para buscar dentro del email", example = "correo", required = true)
            @RequestParam String texto) {
        List<EntityModel<ClienteDTO>> clientes = clienteService.buscarPorEmail(texto)
                .stream()
                .map(clienteModelAssembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(
                clientes,
                linkTo(methodOn(ClienteControllerV2.class).buscarPorEmail(texto)).withSelfRel(),
                linkTo(methodOn(ClienteControllerV2.class).findAll()).withRel("clientes")
        ));
    }
}