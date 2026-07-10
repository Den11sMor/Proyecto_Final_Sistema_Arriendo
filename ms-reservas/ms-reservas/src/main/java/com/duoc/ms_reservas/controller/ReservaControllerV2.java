package com.duoc.ms_reservas.controller;

import com.duoc.ms_reservas.assemblers.ReservaModelAssembler;
import com.duoc.ms_reservas.dto.ReservaDTO;
import com.duoc.ms_reservas.dto.ReservaRequestDTO;
import com.duoc.ms_reservas.exception.ErrorResponse;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2")
@Slf4j
@Tag(name = "Reservas V2", description = "Operaciones de reservas con respuestas HATEOAS")
public class ReservaControllerV2 {

    private final ReservaService reservaService;
    private final ReservaModelAssembler reservaModelAssembler;

    @GetMapping("/reservas")
    @Operation(
            summary = "Listar reservas con HATEOAS",
            description = "Retorna todas las reservas registradas con enlaces relacionados"
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
    public ResponseEntity<CollectionModel<EntityModel<ReservaDTO>>> findAll() {
        log.info("Solicitud V2 para listar reservas");
        List<EntityModel<ReservaDTO>> reservas = reservaService.findAll()
                .stream()
                .map(reservaModelAssembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(
                reservas,
                Link.of("/api/v2/reservas").withSelfRel()
        ));
    }

    @GetMapping("/reservas/{id}")
    @Operation(
            summary = "Buscar reserva por ID con HATEOAS",
            description = "Retorna una reserva segun su identificador con enlaces relacionados"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reserva encontrada"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EntityModel<ReservaDTO>> findById(
            @Parameter(description = "ID de la reserva", example = "1", required = true)
            @PathVariable Integer id) {
        log.info("Solicitud V2 para buscar reserva con id: {}", id);
        ReservaDTO reserva = reservaService.findById(id);
        return ResponseEntity.ok(reservaModelAssembler.toModel(reserva));
    }

    @PostMapping("/reservas")
    @Operation(
            summary = "Crear reserva con HATEOAS",
            description = "Registra una nueva reserva y retorna enlaces relacionados"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Reserva creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Cliente vehiculo o estado de reserva no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EntityModel<ReservaDTO>> save(
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
        log.info("Solicitud V2 para crear reserva de cliente {} y vehiculo {}", requestDTO.getClienteId(), requestDTO.getVehiculoId());
        ReservaDTO reservaCreada = reservaService.save(requestDTO);

        return ResponseEntity
                .created(URI.create("/api/v2/reservas/" + reservaCreada.getId()))
                .body(reservaModelAssembler.toModel(reservaCreada));
    }

    @PutMapping("/reservas/{id}")
    @Operation(
            summary = "Actualizar reserva con HATEOAS",
            description = "Actualiza los datos de una reserva existente y retorna enlaces relacionados"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reserva actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Reserva cliente vehiculo o estado no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EntityModel<ReservaDTO>> update(
            @Parameter(description = "ID de la reserva", example = "1", required = true)
            @PathVariable Integer id,
            @Valid @RequestBody ReservaRequestDTO requestDTO) {
        log.info("Solicitud V2 para actualizar reserva con id: {}", id);
        ReservaDTO reservaActualizada = reservaService.update(id, requestDTO);
        return ResponseEntity.ok(reservaModelAssembler.toModel(reservaActualizada));
    }

    @DeleteMapping("/reservas/{id}")
    @Operation(
            summary = "Eliminar reserva",
            description = "Elimina una reserva segun su identificador"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Reserva eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID de la reserva", example = "1", required = true)
            @PathVariable Integer id) {
        log.info("Solicitud V2 para eliminar reserva con id: {}", id);
        reservaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reservas/desde-fecha")
    @Operation(
            summary = "Buscar reservas desde una fecha con HATEOAS",
            description = "Retorna reservas cuya fecha de inicio sea igual o posterior a la fecha indicada con enlaces relacionados"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reservas encontradas"),
            @ApiResponse(responseCode = "400", description = "Fecha invalida",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<CollectionModel<EntityModel<ReservaDTO>>> findByFechaInicioDesde(
            @Parameter(description = "Fecha minima en formato yyyy-MM-dd", example = "2026-07-01", required = true)
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fecha) {

        log.info("Solicitud V2 para buscar reservas desde fecha: {}", fecha);
        List<EntityModel<ReservaDTO>> reservas = reservaService.findByFechaInicioDesde(fecha)
                .stream()
                .map(reservaModelAssembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(
                reservas,
                Link.of("/api/v2/reservas/desde-fecha?fecha=" + fecha).withSelfRel(),
                Link.of("/api/v2/reservas").withRel("reservas")
        ));
    }
}
