package com.duoc.ms_clientes.controller;

import com.duoc.ms_clientes.assemblers.ClienteModelAssembler;
import com.duoc.ms_clientes.dto.ClienteDTO;
import com.duoc.ms_clientes.dto.ClienteRequestDTO;
import com.duoc.ms_clientes.service.ClienteService;
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
public class ClienteControllerV2 {

    private final ClienteService clienteService;
    private final ClienteModelAssembler clienteModelAssembler;

    @GetMapping("/clientes")
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
    public ResponseEntity<EntityModel<ClienteDTO>> findById(@PathVariable Integer id) {
        ClienteDTO cliente = clienteService.findById(id);
        return ResponseEntity.ok(clienteModelAssembler.toModel(cliente));
    }

    @PostMapping("/clientes")
    public ResponseEntity<EntityModel<ClienteDTO>> save(@Valid @RequestBody ClienteRequestDTO request) {
        ClienteDTO clienteCreado = clienteService.save(request);

        return ResponseEntity
                .created(linkTo(methodOn(ClienteControllerV2.class).findById(clienteCreado.getId())).toUri())
                .body(clienteModelAssembler.toModel(clienteCreado));
    }

    @PutMapping("/clientes/{id}")
    public ResponseEntity<EntityModel<ClienteDTO>> update(@PathVariable Integer id,
                                                          @Valid @RequestBody ClienteRequestDTO request) {
        ClienteDTO clienteActualizado = clienteService.update(id, request);
        return ResponseEntity.ok(clienteModelAssembler.toModel(clienteActualizado));
    }

    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/clientes/buscar-email")
    public ResponseEntity<CollectionModel<EntityModel<ClienteDTO>>> buscarPorEmail(@RequestParam String texto) {
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