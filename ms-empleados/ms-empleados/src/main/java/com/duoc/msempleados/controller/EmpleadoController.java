package com.duoc.msempleados.controller;

import com.duoc.msempleados.dto.EmpleadoDTO;
import com.duoc.msempleados.dto.EmpleadoRequestDTO;
import com.duoc.msempleados.service.EmpleadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    @GetMapping("/empleados")
    public ResponseEntity<List<EmpleadoDTO>> findAll(){
        return ResponseEntity.ok(empleadoService.findAll());
    }

    @GetMapping("/empleados/{id}")
    public ResponseEntity<EmpleadoDTO> findById(@PathVariable Integer id){
        return  ResponseEntity.ok(empleadoService.findById(id));
    }

    @PostMapping("/empleados")
    public ResponseEntity<EmpleadoDTO> save(@Valid @RequestBody EmpleadoRequestDTO request){
        EmpleadoDTO empleadoCreado = empleadoService.save(request);
        return ResponseEntity.status(201).body(empleadoCreado);
    }

    @PutMapping("/empleados/{id}")
    public ResponseEntity<EmpleadoDTO> update(@PathVariable Integer id,
                                              @Valid @RequestBody EmpleadoRequestDTO request){
        return  ResponseEntity.ok(empleadoService.update(id, request));
    }

    @DeleteMapping("/empleados/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        empleadoService.delete(id);
        return  ResponseEntity.noContent().build();
    }

    @GetMapping("/activos/anio/{anio}")
    public ResponseEntity<List<EmpleadoDTO>> listarActivosPorAnio(@PathVariable Integer anio) {
        return ResponseEntity.ok(empleadoService.listarActivosPorAnio(anio));
    }


}
