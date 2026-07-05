package com.duoc.ms_reservas.controller;

import com.duoc.ms_reservas.assemblers.ReservaModelAssembler;
import com.duoc.ms_reservas.dto.ReservaDTO;
import com.duoc.ms_reservas.dto.ReservaRequestDTO;
import com.duoc.ms_reservas.service.ReservaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2")
public class ReservaControllerV2 {

    private final ReservaService reservaService;
    private final ReservaModelAssembler reservaModelAssembler;

    @GetMapping("/reservas")
    public ResponseEntity<CollectionModel<EntityModel<ReservaDTO>>> findAll() {
        List<EntityModel<ReservaDTO>> reservas = reservaService.findAll()
                .stream()
                .map(reservaModelAssembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(
                reservas,
                linkTo(methodOn(ReservaControllerV2.class).findAll()).withSelfRel()
        ));
    }

    @GetMapping("/reservas/{id}")
    public ResponseEntity<EntityModel<ReservaDTO>> findById(@PathVariable Integer id) {
        ReservaDTO reserva = reservaService.findById(id);
        return ResponseEntity.ok(reservaModelAssembler.toModel(reserva));
    }

    @PostMapping("/reservas")
    public ResponseEntity<EntityModel<ReservaDTO>> save(@Valid @RequestBody ReservaRequestDTO requestDTO) {
        ReservaDTO reservaCreada = reservaService.save(requestDTO);

        return ResponseEntity
                .created(linkTo(methodOn(ReservaControllerV2.class).findById(reservaCreada.getId())).toUri())
                .body(reservaModelAssembler.toModel(reservaCreada));
    }

    @PutMapping("/reservas/{id}")
    public ResponseEntity<EntityModel<ReservaDTO>> update(@PathVariable Integer id,
                                                          @Valid @RequestBody ReservaRequestDTO requestDTO) {
        ReservaDTO reservaActualizada = reservaService.update(id, requestDTO);
        return ResponseEntity.ok(reservaModelAssembler.toModel(reservaActualizada));
    }

    @DeleteMapping("/reservas/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        reservaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reservas/desde-fecha")
    public ResponseEntity<CollectionModel<EntityModel<ReservaDTO>>> findByFechaInicioDesde(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fecha) {

        List<EntityModel<ReservaDTO>> reservas = reservaService.findByFechaInicioDesde(fecha)
                .stream()
                .map(reservaModelAssembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(
                reservas,
                linkTo(methodOn(ReservaControllerV2.class).findByFechaInicioDesde(fecha)).withSelfRel(),
                linkTo(methodOn(ReservaControllerV2.class).findAll()).withRel("reservas")
        ));
    }
}
