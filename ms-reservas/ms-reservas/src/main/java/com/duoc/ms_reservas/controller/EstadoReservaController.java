package com.duoc.ms_reservas.controller;

import com.duoc.ms_reservas.dto.EstadoReservaDTO;
import com.duoc.ms_reservas.dto.EstadoReservaRequestDTO;
import com.duoc.ms_reservas.service.EstadoReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Estados de Reserva", description = "Operaciones CRUD para la gestión de estados de reserva (Pendiente, Confirmada, Cancelada, etc.)")
public class EstadoReservaController {

    private final EstadoReservaService estadoReservaService;
    // ─────────────────────────────────────────────────────────────────────────
    // GET /estados-reserva
    // ─────────────────────────────────────────────────────────────────────────
    @Operation(
            summary = "Listar todos los estados de reserva",
            description = "Retorna la lista completa de estados de reserva registrados en el sistema."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de estados obtenida exitosamente",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = EstadoReservaDTO.class))
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/estados-reserva")
    public ResponseEntity<List<EstadoReservaDTO>> findAll() {
        return ResponseEntity.ok(estadoReservaService.findAll());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // GET /estados-reserva/{id}
    // ─────────────────────────────────────────────────────────────────────────
    @Operation(
            summary = "Obtener un estado de reserva por ID",
            description = "Busca y retorna un estado de reserva específico según su identificador único."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Estado de reserva encontrado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = EstadoReservaDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Estado de reserva no encontrado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = "{\"mensaje\": \"Estado de reserva no encontrado con id: 99\"}")
                    )
            )
    })
    @GetMapping("/estados-reserva/{id}")
    public ResponseEntity<EstadoReservaDTO> findById(
            @Parameter(description = "ID del estado de reserva", example = "1", required = true)
            @PathVariable Integer id) {
        return ResponseEntity.ok(estadoReservaService.findById(id));
    }

    // ─────────────────────────────────────────────────────────────────────────
    // POST /estados-reserva
    // ─────────────────────────────────────────────────────────────────────────
    @Operation(
            summary = "Crear un nuevo estado de reserva",
            description = """
                    Crea un nuevo estado de reserva en el sistema.
                    
                    **Validaciones aplicadas:**
                    - El nombre debe tener entre 2 y 50 caracteres.
                    - La descripción debe tener entre 5 y 150 caracteres.
                    - La prioridad debe ser mayor o igual a 1.
                    - La fecha de creación no puede ser futura.
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Estado de reserva creado exitosamente",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = EstadoReservaDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    value = "{\"nombre\": \"El nombre del estado es obligatorio\"}"
                            )
                    )
            )
    })
    @PostMapping("/estados-reserva")
    public ResponseEntity<EstadoReservaDTO> save(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del nuevo estado de reserva",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = EstadoReservaRequestDTO.class),
                            examples = @ExampleObject(
                                    name = "Ejemplo de estado",
                                    value = """
                                            {
                                              "nombre": "Pendiente",
                                              "descripcion": "La reserva está en espera de confirmación",
                                              "prioridad": 1,
                                              "activo": true,
                                              "fechaCreacion": "2025-06-01T10:00:00"
                                            }
                                            """
                            )
                    )
            )
            @Valid @RequestBody EstadoReservaRequestDTO requestDTO) {

        EstadoReservaDTO estadoCreado = estadoReservaService.save(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(estadoCreado);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // PUT /estados-reserva/{id}
    // ─────────────────────────────────────────────────────────────────────────
    @Operation(
            summary = "Actualizar un estado de reserva existente",
            description = "Actualiza todos los campos de un estado de reserva identificado por su ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Estado de reserva actualizado exitosamente",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = EstadoReservaDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Estado de reserva no encontrado")
    })
    @PutMapping("/estados-reserva/{id}")
    public ResponseEntity<EstadoReservaDTO> update(
            @Parameter(description = "ID del estado de reserva a actualizar", example = "1", required = true)
            @PathVariable Integer id,
            @Valid @RequestBody EstadoReservaRequestDTO requestDTO) {

        return ResponseEntity.ok(estadoReservaService.update(id, requestDTO));
    }

    // ─────────────────────────────────────────────────────────────────────────
    // DELETE /estados-reserva/{id}
    // ─────────────────────────────────────────────────────────────────────────
    @Operation(
            summary = "Eliminar un estado de reserva",
            description = "Elimina permanentemente un estado de reserva del sistema según su ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Estado de reserva eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Estado de reserva no encontrado")
    })
    @DeleteMapping("/estados-reserva/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID del estado de reserva a eliminar", example = "1", required = true)
            @PathVariable Integer id) {

        estadoReservaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
