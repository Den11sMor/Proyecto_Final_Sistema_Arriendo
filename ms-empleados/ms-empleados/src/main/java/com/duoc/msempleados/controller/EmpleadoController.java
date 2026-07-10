package com.duoc.msempleados.controller;

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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@Tag(name = "Empleados", description = "Operaciones CRUD y consultas de empleados")
@Slf4j
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    @GetMapping("/empleados")
    @Operation(summary = "Listar empleados", description = "Retorna todos los empleados registrados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empleados encontrados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<List<EmpleadoDTO>> findAll() {
        log.info("Solicitud para listar empleados");
        return ResponseEntity.ok(empleadoService.findAll());
    }

    @GetMapping("/empleados/{id}")
    @Operation(summary = "Buscar empleado por ID", description = "Retorna un empleado segun su identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empleado encontrado"),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EmpleadoDTO> findById(@PathVariable Integer id) {
        log.info("Solicitud para buscar empleado por id {}", id);
        return ResponseEntity.ok(empleadoService.findById(id));
    }

    @PostMapping("/empleados")
    @Operation(summary = "Crear empleado", description = "Registra un nuevo empleado")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Empleado creado"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EmpleadoDTO> save(@Valid @RequestBody EmpleadoRequestDTO request) {
        log.info("Solicitud para crear empleado con rut {}", request.getRut());
        EmpleadoDTO empleadoCreado = empleadoService.save(request);
        return ResponseEntity.status(201).body(empleadoCreado);
    }

    @PutMapping("/empleados/{id}")
    @Operation(summary = "Actualizar empleado", description = "Actualiza los datos de un empleado existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empleado actualizado"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos"),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EmpleadoDTO> update(@PathVariable Integer id,
                                              @Valid @RequestBody EmpleadoRequestDTO request) {
        log.info("Solicitud para actualizar empleado con id {}", id);
        return ResponseEntity.ok(empleadoService.update(id, request));
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
        log.info("Solicitud para eliminar empleado con id {}", id);
        empleadoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/activos/anio/{anio}")
    @Operation(summary = "Listar empleados activos por anio", description = "Retorna empleados activos ingresados en un anio especifico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empleados activos encontrados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<List<EmpleadoDTO>> listarActivosPorAnio(@PathVariable Integer anio) {
        log.info("Solicitud para listar empleados activos del anio {}", anio);
        return ResponseEntity.ok(empleadoService.listarActivosPorAnio(anio));
    }
}
