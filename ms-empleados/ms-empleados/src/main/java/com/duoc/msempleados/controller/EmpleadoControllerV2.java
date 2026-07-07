package com.duoc.msempleados.controller;

import com.duoc.msempleados.assemblers.EmpleadoModelAssembler;
import com.duoc.msempleados.dto.EmpleadoDTO;
import com.duoc.msempleados.dto.EmpleadoRequestDTO;
import com.duoc.msempleados.service.EmpleadoService;
import io.swagger.v3.oas.annotations.Operation;
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
@Tag(name = "Empleados V2", description = "Operaciones de empleados con respuestas HATEOAS")
public class EmpleadoControllerV2 {

    private final EmpleadoService empleadoService;
    private final EmpleadoModelAssembler empleadoModelAssembler;

    @GetMapping("/empleados")
    @Operation(summary = "Listar empleados con HATEOAS", description = "Retorna todos los empleados con enlaces relacionados")
    public ResponseEntity<CollectionModel<EntityModel<EmpleadoDTO>>> findAll() {
        List<EntityModel<EmpleadoDTO>> empleados = empleadoService.findAll()
                .stream()
                .map(empleadoModelAssembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(
                empleados,
                linkTo(methodOn(EmpleadoControllerV2.class).findAll()).withSelfRel()
        ));
    }

    @GetMapping("/empleados/{id}")
    @Operation(summary = "Buscar empleado por ID con HATEOAS", description = "Retorna un empleado con enlaces relacionados")
    public ResponseEntity<EntityModel<EmpleadoDTO>> findById(@PathVariable Integer id) {
        EmpleadoDTO empleado = empleadoService.findById(id);
        return ResponseEntity.ok(empleadoModelAssembler.toModel(empleado));
    }

    @PostMapping("/empleados")
    @Operation(summary = "Crear empleado con HATEOAS", description = "Registra un nuevo empleado y retorna enlaces relacionados")
    public ResponseEntity<EntityModel<EmpleadoDTO>> save(@Valid @RequestBody EmpleadoRequestDTO request) {
        EmpleadoDTO empleadoCreado = empleadoService.save(request);

        return ResponseEntity
                .created(linkTo(methodOn(EmpleadoControllerV2.class).findById(empleadoCreado.getId())).toUri())
                .body(empleadoModelAssembler.toModel(empleadoCreado));
    }

    @PutMapping("/empleados/{id}")
    @Operation(summary = "Actualizar empleado con HATEOAS", description = "Actualiza un empleado existente y retorna enlaces relacionados")
    public ResponseEntity<EntityModel<EmpleadoDTO>> update(@PathVariable Integer id,
                                                           @Valid @RequestBody EmpleadoRequestDTO request) {
        EmpleadoDTO empleadoActualizado = empleadoService.update(id, request);
        return ResponseEntity.ok(empleadoModelAssembler.toModel(empleadoActualizado));
    }

    @DeleteMapping("/empleados/{id}")
    @Operation(summary = "Eliminar empleado", description = "Elimina un empleado segun su identificador")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        empleadoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/activos/anio/{anio}")
    @Operation(summary = "Listar empleados activos por anio con HATEOAS", description = "Retorna empleados activos ingresados en un anio especifico")
    public ResponseEntity<CollectionModel<EntityModel<EmpleadoDTO>>> listarActivosPorAnio(@PathVariable Integer anio) {
        List<EntityModel<EmpleadoDTO>> empleados = empleadoService.listarActivosPorAnio(anio)
                .stream()
                .map(empleadoModelAssembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(
                empleados,
                linkTo(methodOn(EmpleadoControllerV2.class).listarActivosPorAnio(anio)).withSelfRel(),
                linkTo(methodOn(EmpleadoControllerV2.class).findAll()).withRel("empleados")
        ));
    }
}