package com.duoc.ms_clientes.controller;

import com.duoc.ms_clientes.assemblers.DireccionModelAssembler;
import com.duoc.ms_clientes.dto.DireccionDTO;
import com.duoc.ms_clientes.dto.DireccionRequestDTO;
import com.duoc.ms_clientes.service.DireccionService;
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
public class DireccionControllerV2 {

    private final DireccionService direccionService;
    private final DireccionModelAssembler direccionModelAssembler;

    @GetMapping("/direcciones")
    public ResponseEntity<CollectionModel<EntityModel<DireccionDTO>>> findAll() {
        List<EntityModel<DireccionDTO>> direcciones = direccionService.findAll()
                .stream()
                .map(direccionModelAssembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(
                direcciones,
                linkTo(methodOn(DireccionControllerV2.class).findAll()).withSelfRel()
        ));
    }

    @GetMapping("/direcciones/{id}")
    public ResponseEntity<EntityModel<DireccionDTO>> findById(@PathVariable Integer id) {
        DireccionDTO direccion = direccionService.findById(id);
        return ResponseEntity.ok(direccionModelAssembler.toModel(direccion));
    }

    @PostMapping("/direcciones")
    public ResponseEntity<EntityModel<DireccionDTO>> save(@Valid @RequestBody DireccionRequestDTO request) {
        DireccionDTO direccionCreada = direccionService.save(request);

        return ResponseEntity
                .created(linkTo(methodOn(DireccionControllerV2.class).findById(direccionCreada.getId())).toUri())
                .body(direccionModelAssembler.toModel(direccionCreada));
    }

    @PutMapping("/direcciones/{id}")
    public ResponseEntity<EntityModel<DireccionDTO>> update(@PathVariable Integer id,
                                                            @Valid @RequestBody DireccionRequestDTO request) {
        DireccionDTO direccionActualizada = direccionService.update(id, request);
        return ResponseEntity.ok(direccionModelAssembler.toModel(direccionActualizada));
    }

    @DeleteMapping("/direcciones/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        direccionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}