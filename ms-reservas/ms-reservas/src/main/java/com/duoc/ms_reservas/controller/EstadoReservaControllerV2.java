package com.duoc.ms_reservas.controller;

import com.duoc.ms_reservas.assemblers.EstadoReservaModelAssembler;
import com.duoc.ms_reservas.dto.EstadoReservaDTO;
import com.duoc.ms_reservas.dto.EstadoReservaRequestDTO;
import com.duoc.ms_reservas.service.EstadoReservaService;
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
public class EstadoReservaControllerV2 {

    private final EstadoReservaService estadoReservaService;
    private final EstadoReservaModelAssembler estadoReservaModelAssembler;

    @GetMapping("/estados-reserva")
    public ResponseEntity<CollectionModel<EntityModel<EstadoReservaDTO>>> findAll() {
        List<EntityModel<EstadoReservaDTO>> estados = estadoReservaService.findAll()
                .stream()
                .map(estadoReservaModelAssembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(
                estados,
                linkTo(methodOn(EstadoReservaControllerV2.class).findAll()).withSelfRel()
        ));
    }

    @GetMapping("/estados-reserva/{id}")
    public ResponseEntity<EntityModel<EstadoReservaDTO>> findById(@PathVariable Integer id) {
        EstadoReservaDTO estado = estadoReservaService.findById(id);
        return ResponseEntity.ok(estadoReservaModelAssembler.toModel(estado));
    }

    @PostMapping("/estados-reserva")
    public ResponseEntity<EntityModel<EstadoReservaDTO>> save(@Valid @RequestBody EstadoReservaRequestDTO requestDTO) {
        EstadoReservaDTO estadoCreado = estadoReservaService.save(requestDTO);

        return ResponseEntity
                .created(linkTo(methodOn(EstadoReservaControllerV2.class).findById(estadoCreado.getId())).toUri())
                .body(estadoReservaModelAssembler.toModel(estadoCreado));
    }

    @PutMapping("/estados-reserva/{id}")
    public ResponseEntity<EntityModel<EstadoReservaDTO>> update(@PathVariable Integer id,
                                                                @Valid @RequestBody EstadoReservaRequestDTO requestDTO) {
        EstadoReservaDTO estadoActualizado = estadoReservaService.update(id, requestDTO);
        return ResponseEntity.ok(estadoReservaModelAssembler.toModel(estadoActualizado));
    }

    @DeleteMapping("/estados-reserva/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        estadoReservaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
