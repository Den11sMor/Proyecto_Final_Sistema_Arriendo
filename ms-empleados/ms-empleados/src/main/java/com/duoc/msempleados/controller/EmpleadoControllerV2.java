package com.duoc.msempleados.controller;

import com.duoc.msempleados.assemblers.EmpleadoModelAssembler;
import com.duoc.msempleados.dto.EmpleadoDTO;
import com.duoc.msempleados.dto.EmpleadoRequestDTO;
import com.duoc.msempleados.exception.ErrorResponse;
import com.duoc.msempleados.service.EmpleadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2")
@Tag(name = "Empleados V2", description = "Operaciones de empleados con respuestas HATEOAS")
@Slf4j
public class EmpleadoControllerV2 {

    private final EmpleadoService empleadoService;
    private final EmpleadoModelAssembler empleadoModelAssembler;

    @GetMapping("/empleados")
    @Operation(summary = "Listar empleados con HATEOAS", description = "Retorna todos los empleados con enlaces relacionados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empleados encontrados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<CollectionModel<EntityModel<EmpleadoDTO>>> findAll() {
        log.info("Solicitud V2 para listar empleados");
        List<EntityModel<EmpleadoDTO>> empleados = empleadoService.findAll()
                .stream()
                .map(empleadoModelAssembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(
                empleados,
                Link.of("/api/v2/empleados").withSelfRel()
        ));
    }

    @GetMapping("/empleados/{id}")
    @Operation(summary = "Buscar empleado por ID con HATEOAS", description = "Retorna un empleado con enlaces relacionados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empleado encontrado"),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EntityModel<EmpleadoDTO>> findById(@PathVariable Integer id) {
        log.info("Solicitud V2 para buscar empleado por id {}", id);
        EmpleadoDTO empleado = empleadoService.findById(id);
        return ResponseEntity.ok(empleadoModelAssembler.toModel(empleado));
    }

    @PostMapping("/empleados")
    @Operation(summary = "Crear empleado con HATEOAS", description = "Registra un nuevo empleado y retorna enlaces relacionados")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Empleado creado"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EntityModel<EmpleadoDTO>> save(@Valid @RequestBody EmpleadoRequestDTO request) {
        log.info("Solicitud V2 para crear empleado con rut {}", request.getRut());
        EmpleadoDTO empleadoCreado = empleadoService.save(request);

        return ResponseEntity
                .created(URI.create("/api/v2/empleados/" + empleadoCreado.getId()))
                .body(empleadoModelAssembler.toModel(empleadoCreado));
    }

    @PutMapping("/empleados/{id}")
    @Operation(summary = "Actualizar empleado con HATEOAS", description = "Actualiza un empleado existente y retorna enlaces relacionados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empleado actualizado"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos"),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EntityModel<EmpleadoDTO>> update(@PathVariable Integer id,
                                                           @Valid @RequestBody EmpleadoRequestDTO request) {
        log.info("Solicitud V2 para actualizar empleado con id {}", id);
        EmpleadoDTO empleadoActualizado = empleadoService.update(id, request);
        return ResponseEntity.ok(empleadoModelAssembler.toModel(empleadoActualizado));
    }

    @DeleteMapping("/empleados/{id}")
    @Operation(summary = "Eliminar empleado", description = "Elimina un empleado segun su identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Empleado eliminado"),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        log.info("Solicitud V2 para eliminar empleado con id {}", id);
        empleadoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/activos/anio/{anio}")
    @Operation(summary = "Listar empleados activos por anio con HATEOAS", description = "Retorna empleados activos ingresados en un anio especifico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empleados activos encontrados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<CollectionModel<EntityModel<EmpleadoDTO>>> listarActivosPorAnio(@PathVariable Integer anio) {
        log.info("Solicitud V2 para listar empleados activos del anio {}", anio);
        List<EntityModel<EmpleadoDTO>> empleados = empleadoService.listarActivosPorAnio(anio)
                .stream()
                .map(empleadoModelAssembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(
                empleados,
                Link.of("/api/v2/activos/anio/" + anio).withSelfRel(),
                Link.of("/api/v2/empleados").withRel("empleados")
        ));
    }
}
