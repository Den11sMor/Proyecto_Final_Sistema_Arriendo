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
@Tag(name = "Reservas", description = "Operaciones CRUD y consultas de reservas")
public class ReservaController {

    private final ReservaService reservaService;

    @GetMapping("/reservas")
    @Operation(
            summary = "Listar reservas",
            description = "Retorna todas las reservas registradas en el sistema"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Reservas obtenidas correctamente",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ReservaDTO.class))
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<ReservaDTO>> findAll() {
        return ResponseEntity.ok(reservaService.findAll());
    }

    @GetMapping("/reservas/{id}")
    @Operation(
            summary = "Buscar reserva por ID",
            description = "Retorna una reserva segun su identificador"
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
                            examples = @ExampleObject(value = "{\"mensaje\":\"Reserva no encontrada con id: 99\"}")
                    )
            )
    })
    public ResponseEntity<ReservaDTO> findById(
            @Parameter(description = "ID de la reserva", example = "1", required = true)
            @PathVariable Integer id) {
        return ResponseEntity.ok(reservaService.findById(id));
    }

    @PostMapping("/reservas")
    @Operation(
            summary = "Crear reserva",
            description = """
                    Registra una nueva reserva en el sistema

                    Validaciones principales
                    - El cliente debe existir
                    - El vehiculo debe existir
                    - La fecha de inicio no debe ser pasada
                    - La fecha de fin debe ser igual o posterior a la fecha de inicio
                    - La cantidad de dias debe ser mayor o igual a 1
                    - El monto total debe ser mayor a 0
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Reserva creada correctamente",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ReservaDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada invalidos",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = "{\"clienteId\":\"El id del cliente es obligatorio\"}")
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cliente vehiculo o estado de reserva no encontrado"
            ),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ReservaDTO> save(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos requeridos para crear una reserva",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ReservaRequestDTO.class),
                            examples = @ExampleObject(
                                    name = "Reserva",
                                    value = """
                                            {
                                              "clienteId": 1,
                                              "vehiculoId": 2,
                                              "fechaInicio": "2026-07-01",
                                              "fechaFin": "2026-07-05",
                                              "cantidadDias": 4,
                                              "montoTotal": 120000,
                                              "observacion": "Reserva para viaje",
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

    @PutMapping("/reservas/{id}")
    @Operation(
            summary = "Actualizar reserva",
            description = "Actualiza los datos de una reserva existente"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Reserva actualizada correctamente",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ReservaDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos"),
            @ApiResponse(responseCode = "404", description = "Reserva cliente vehiculo o estado no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ReservaDTO> update(
            @Parameter(description = "ID de la reserva", example = "1", required = true)
            @PathVariable Integer id,
            @Valid @RequestBody ReservaRequestDTO requestDTO) {
        return ResponseEntity.ok(reservaService.update(id, requestDTO));
    }

    @DeleteMapping("/reservas/{id}")
    @Operation(
            summary = "Eliminar reserva",
            description = "Elimina una reserva segun su identificador"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Reserva eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID de la reserva", example = "1", required = true)
            @PathVariable Integer id) {
        reservaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reservas/desde-fecha")
    @Operation(
            summary = "Buscar reservas desde una fecha",
            description = "Retorna reservas cuya fecha de inicio sea igual o posterior a la fecha indicada"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Reservas encontradas",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ReservaDTO.class))
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Fecha invalida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<ReservaDTO>> findByFechaInicioDesde(
            @Parameter(description = "Fecha minima en formato yyyy-MM-dd", example = "2026-07-01", required = true)
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fecha) {
        return ResponseEntity.ok(reservaService.findByFechaInicioDesde(fecha));
    }
}