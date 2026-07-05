package com.duoc.ms_reservas.controller;

import com.duoc.ms_reservas.dto.ReservaDTO;
import com.duoc.ms_reservas.dto.ReservaRequestDTO;
import com.duoc.ms_reservas.service.ReservaService;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Reservas", description = "Operaciones CRUD para la gestión de reservas de vehículos")
public class ReservaController {

    private final ReservaService reservaService;

    // ─────────────────────────────────────────────────────────────────────────
    // GET /reservas
    // ─────────────────────────────────────────────────────────────────────────
    @Operation(
            summary = "Listar todas las reservas",
            description = "Retorna la lista completa de reservas registradas en el sistema."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de reservas obtenida exitosamente",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ReservaDTO.class))
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/reservas")
    public ResponseEntity<List<ReservaDTO>> findAll() {
        return ResponseEntity.ok(reservaService.findAll());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // GET /reservas/{id}
    // ─────────────────────────────────────────────────────────────────────────
    @Operation(
            summary = "Obtener una reserva por ID",
            description = "Busca y retorna una reserva específica según su identificador único."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Reserva encontrada",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ReservaDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Reserva no encontrada",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = "{\"mensaje\": \"Reserva no encontrada con id: 99\"}")
                    )
            )
    })
    @GetMapping("/reservas/{id}")
    public ResponseEntity<ReservaDTO> findById(
            @Parameter(description = "ID de la reserva", example = "1", required = true)
            @PathVariable Integer id) {
        return ResponseEntity.ok(reservaService.findById(id));
    }

    // ─────────────────────────────────────────────────────────────────────────
    // POST /reservas
    // ─────────────────────────────────────────────────────────────────────────
    @Operation(
            summary = "Crear una nueva reserva",
            description = """
                    Crea una nueva reserva en el sistema.
                    
                    **Validaciones aplicadas:**
                    - El cliente debe existir en ms-clientes (puerto 8081).
                    - El vehículo debe existir y estar disponible en ms-vehiculos (puerto 8082).
                    - Las fechas de inicio y fin deben ser presentes o futuras.
                    - La cantidad de días debe ser mayor o igual a 1.
                    - El monto total debe ser mayor a 0.
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Reserva creada exitosamente",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ReservaDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos (errores de validación)",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    value = "{\"clienteId\": \"El id del cliente es obligatorio\", \"fechaInicio\": \"La fecha de inicio no puede ser pasada\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cliente o vehículo no encontrado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = "{\"mensaje\": \"Cliente no encontrado con id: 5\"}")
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "El vehículo no está disponible o error interno"
            )
    })
    @PostMapping("/reservas")
    public ResponseEntity<ReservaDTO> save(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la nueva reserva",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ReservaRequestDTO.class),
                            examples = @ExampleObject(
                                    name = "Ejemplo de reserva",
                                    value = """
                                            {
                                              "clienteId": 1,
                                              "vehiculoId": 2,
                                              "fechaInicio": "2026-07-01",
                                              "fechaFin": "2026-07-05",
                                              "cantidadDias": 4,
                                              "montoTotal": 120000,
                                              "observacion": "Reserva para viaje de negocios",
                                              "activa": true,
                                              "estadoReservaId": 1
                                            }
                                            """
                            )
                    )
            )
            @Valid @RequestBody ReservaRequestDTO requestDTO) {

        ReservaDTO reservaCreada = reservaService.save(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaCreada);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // PUT /reservas/id
    // ─────────────────────────────────────────────────────────────────────────
    @Operation(
            summary = "Actualizar una reserva existente",
            description = "Actualiza todos los campos de una reserva identificada por su ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Reserva actualizada exitosamente",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ReservaDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Reserva, cliente o vehículo no encontrado")
    })
    @PutMapping("/reservas/{id}")
    public ResponseEntity<ReservaDTO> update(
            @Parameter(description = "ID de la reserva a actualizar", example = "1", required = true)
            @PathVariable Integer id,
            @Valid @RequestBody ReservaRequestDTO requestDTO) {

        return ResponseEntity.ok(reservaService.update(id, requestDTO));
    }

    // ─────────────────────────────────────────────────────────────────────────
    // DELETE /reservas/id
    // ─────────────────────────────────────────────────────────────────────────
    @Operation(
            summary = "Eliminar una reserva",
            description = "Elimina permanentemente una reserva del sistema según su ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Reserva eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    @DeleteMapping("/reservas/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID de la reserva a eliminar", example = "1", required = true)
            @PathVariable Integer id) {

        reservaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // GET /reservas/desde-fecha
    // ─────────────────────────────────────────────────────────────────────────
    @Operation(
            summary = "Buscar reservas desde una fecha",
            description = "Retorna todas las reservas cuya fecha de inicio sea igual o posterior a la fecha indicada. Ordenadas de más reciente a más antigua."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Reservas encontradas",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ReservaDTO.class))
                    )
            )
    })
    @GetMapping("/reservas/desde-fecha")
    public ResponseEntity<List<ReservaDTO>> findByFechaInicioDesde(
            @Parameter(
                    description = "Fecha de inicio mínima (formato ISO: yyyy-MM-dd)",
                    example = "2026-07-01",
                    required = true
            )
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fecha) {

        return ResponseEntity.ok(reservaService.findByFechaInicioDesde(fecha));
    }
}