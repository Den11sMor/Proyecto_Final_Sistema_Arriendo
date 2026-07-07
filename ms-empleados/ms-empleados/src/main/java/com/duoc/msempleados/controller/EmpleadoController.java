package com.duoc.msempleados.controller;

import com.duoc.msempleados.dto.EmpleadoDTO;
import com.duoc.msempleados.dto.EmpleadoRequestDTO;
import com.duoc.msempleados.service.EmpleadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@Tag(name = "Empleados", description = "Operaciones CRUD y consultas de empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    @GetMapping("/empleados")
    @Operation(summary = "Listar empleados", description = "Retorna todos los empleados registrados")
    public ResponseEntity<List<EmpleadoDTO>> findAll() {
        return ResponseEntity.ok(empleadoService.findAll());
    }

    @GetMapping("/empleados/{id}")
    @Operation(summary = "Buscar empleado por ID", description = "Retorna un empleado segun su identificador")
    public ResponseEntity<EmpleadoDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(empleadoService.findById(id));
    }

    @PostMapping("/empleados")
    @Operation(summary = "Crear empleado", description = "Registra un nuevo empleado")
    public ResponseEntity<EmpleadoDTO> save(@Valid @RequestBody EmpleadoRequestDTO request) {
        EmpleadoDTO empleadoCreado = empleadoService.save(request);
        return ResponseEntity.status(201).body(empleadoCreado);
    }

    @PutMapping("/empleados/{id}")
    @Operation(summary = "Actualizar empleado", description = "Actualiza los datos de un empleado existente")
    public ResponseEntity<EmpleadoDTO> update(@PathVariable Integer id,
                                              @Valid @RequestBody EmpleadoRequestDTO request) {
        return ResponseEntity.ok(empleadoService.update(id, request));
    }

    @DeleteMapping("/empleados/{id}")
    @Operation(summary = "Eliminar empleado", description = "Elimina un empleado segun su identificador")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        empleadoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/activos/anio/{anio}")
    @Operation(summary = "Listar empleados activos por anio", description = "Retorna empleados activos ingresados en un anio especifico")
    public ResponseEntity<List<EmpleadoDTO>> listarActivosPorAnio(@PathVariable Integer anio) {
        return ResponseEntity.ok(empleadoService.listarActivosPorAnio(anio));
    }
}