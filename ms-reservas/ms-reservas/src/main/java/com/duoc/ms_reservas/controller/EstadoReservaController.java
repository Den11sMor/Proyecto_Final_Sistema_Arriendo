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
@Tag(name = "Estados de Reserva", description = "Operaciones CRUD de estados de reserva")
public class EstadoReservaController {

    private final EstadoReservaService estadoReservaService;

    @GetMapping("/estados-reserva")
    @Operation(
            summary = "Listar estados de reserva",
            description = "Retorna todos los estados de reserva registrados"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Estados obtenidos correctamente",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = EstadoReservaDTO.class))
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<EstadoReservaDTO>> findAll() {
        return ResponseEntity.ok(estadoReservaService.findAll());
    }

    @GetMapping("/estados-reserva/{id}")
    @Operation(
            summary = "Buscar estado de reserva por ID",
            description = "Retorna un estado de reserva segun su identificador"
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
                            examples = @ExampleObject(value = "{\"mensaje\":\"Estado de reserva no encontrado con id: 99\"}")
                    )
            )
    })
    public ResponseEntity<EstadoReservaDTO> findById(
            @Parameter(description = "ID del estado de reserva", example = "1", required = true)
            @PathVariable Integer id) {
        return ResponseEntity.ok(estadoReservaService.findById(id));
    }

    @PostMapping("/estados-reserva")
    @Operation(
            summary = "Crear estado de reserva",
            description = """
                    Registra un nuevo estado de reserva

                    Validaciones principales
                    - El nombre es obligatorio
                    - La descripcion es obligatoria
                    - La prioridad debe ser mayor o igual a 1
                    - La fecha de creacion no puede ser futura
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Estado de reserva creado correctamente",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = EstadoReservaDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada invalidos",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = "{\"nombre\":\"El nombre del estado es obligatorio\"}")
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<EstadoReservaDTO> save(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos requeridos para crear un estado de reserva",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = EstadoReservaRequestDTO.class),
                            examples = @ExampleObject(
                                    name = "EstadoReserva",
                                    value = """
                                            {
                                              "nombre": "Pendiente",
                                              "descripcion": "Reserva en espera de confirmacion",
                                              "prioridad": 1,
                                              "activo": true,
                                              "fechaCreacion": "2026-07-01"
                                            }
                                            """
                            )
                    )
            )
            @Valid @RequestBody EstadoReservaRequestDTO requestDTO) {
        EstadoReservaDTO estadoCreado = estadoReservaService.save(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(estadoCreado);
    }

    @PutMapping("/estados-reserva/{id}")
    @Operation(
            summary = "Actualizar estado de reserva",
            description = "Actualiza los datos de un estado de reserva existente"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Estado de reserva actualizado correctamente",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = EstadoReservaDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos"),
            @ApiResponse(responseCode = "404", description = "Estado de reserva no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<EstadoReservaDTO> update(
            @Parameter(description = "ID del estado de reserva", example = "1", required = true)
            @PathVariable Integer id,
            @Valid @RequestBody EstadoReservaRequestDTO requestDTO) {
        return ResponseEntity.ok(estadoReservaService.update(id, requestDTO));
    }

    @DeleteMapping("/estados-reserva/{id}")
    @Operation(
            summary = "Eliminar estado de reserva",
            description = "Elimina un estado de reserva segun su identificador"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Estado de reserva eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Estado de reserva no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID del estado de reserva", example = "1", required = true)
            @PathVariable Integer id) {
        estadoReservaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}