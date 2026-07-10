package com.duoc.ms_reservas.controller;

import com.duoc.ms_reservas.assemblers.EstadoReservaModelAssembler;
import com.duoc.ms_reservas.dto.EstadoReservaDTO;
import com.duoc.ms_reservas.dto.EstadoReservaRequestDTO;
import com.duoc.ms_reservas.exception.ErrorResponse;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2")
@Slf4j
@Tag(name = "Estados de Reserva V2", description = "Operaciones de estados de reserva con respuestas HATEOAS")
public class EstadoReservaControllerV2 {

    private final EstadoReservaService estadoReservaService;
    private final EstadoReservaModelAssembler estadoReservaModelAssembler;

    @GetMapping("/estados-reserva")
    @Operation(
            summary = "Listar estados de reserva con HATEOAS",
            description = "Retorna todos los estados de reserva con enlaces relacionados"
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
    public ResponseEntity<CollectionModel<EntityModel<EstadoReservaDTO>>> findAll() {
        log.info("Solicitud V2 para listar estados de reserva");
        List<EntityModel<EstadoReservaDTO>> estados = estadoReservaService.findAll()
                .stream()
                .map(estadoReservaModelAssembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(
                estados,
                Link.of("/api/v2/estados-reserva").withSelfRel()
        ));
    }

    @GetMapping("/estados-reserva/{id}")
    @Operation(
            summary = "Buscar estado de reserva por ID con HATEOAS",
            description = "Retorna un estado de reserva segun su identificador con enlaces relacionados"
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
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<EntityModel<EstadoReservaDTO>> findById(
            @Parameter(description = "ID del estado de reserva", example = "1", required = true)
            @PathVariable Integer id) {
        log.info("Solicitud V2 para buscar estado de reserva con id: {}", id);
        EstadoReservaDTO estado = estadoReservaService.findById(id);
        return ResponseEntity.ok(estadoReservaModelAssembler.toModel(estado));
    }

    @PostMapping("/estados-reserva")
    @Operation(
            summary = "Crear estado de reserva con HATEOAS",
            description = "Registra un nuevo estado de reserva y retorna enlaces relacionados"
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
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EntityModel<EstadoReservaDTO>> save(
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
        log.info("Solicitud V2 para crear estado de reserva: {}", requestDTO.getNombre());
        EstadoReservaDTO estadoCreado = estadoReservaService.save(requestDTO);

        return ResponseEntity
                .created(URI.create("/api/v2/estados-reserva/" + estadoCreado.getId()))
                .body(estadoReservaModelAssembler.toModel(estadoCreado));
    }

    @PutMapping("/estados-reserva/{id}")
    @Operation(
            summary = "Actualizar estado de reserva con HATEOAS",
            description = "Actualiza un estado de reserva existente y retorna enlaces relacionados"
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
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Estado de reserva no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EntityModel<EstadoReservaDTO>> update(
            @Parameter(description = "ID del estado de reserva", example = "1", required = true)
            @PathVariable Integer id,
            @Valid @RequestBody EstadoReservaRequestDTO requestDTO) {
        log.info("Solicitud V2 para actualizar estado de reserva con id: {}", id);
        EstadoReservaDTO estadoActualizado = estadoReservaService.update(id, requestDTO);
        return ResponseEntity.ok(estadoReservaModelAssembler.toModel(estadoActualizado));
    }

    @DeleteMapping("/estados-reserva/{id}")
    @Operation(
            summary = "Eliminar estado de reserva",
            description = "Elimina un estado de reserva segun su identificador"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Estado de reserva eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Estado de reserva no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID del estado de reserva", example = "1", required = true)
            @PathVariable Integer id) {
        log.info("Solicitud V2 para eliminar estado de reserva con id: {}", id);
        estadoReservaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
