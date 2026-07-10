package com.duoc.ms_clientes.controller;

import com.duoc.ms_clientes.assemblers.DireccionModelAssembler;
import com.duoc.ms_clientes.dto.DireccionDTO;
import com.duoc.ms_clientes.dto.DireccionRequestDTO;
import com.duoc.ms_clientes.exception.ErrorResponse;
import com.duoc.ms_clientes.service.DireccionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2")
@Tag(name = "Direcciones V2", description = "Operaciones de direcciones con respuestas HATEOAS")
public class DireccionControllerV2 {

    private final DireccionService direccionService;
    private final DireccionModelAssembler direccionModelAssembler;

    @GetMapping("/direcciones")
    @Operation(
            summary = "Listar direcciones con HATEOAS",
            description = "Retorna todas las direcciones con enlaces HATEOAS"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Direcciones obtenidas correctamente",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CollectionModel.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    public ResponseEntity<CollectionModel<EntityModel<DireccionDTO>>> findAll() {
        log.info("Solicitud V2 para listar direcciones");
        List<EntityModel<DireccionDTO>> direcciones = direccionService.findAll()
                .stream()
                .map(direccionModelAssembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(
                direcciones,
                Link.of("/api/v2/direcciones").withSelfRel()
        ));
    }

    @GetMapping("/direcciones/{id}")
    @Operation(
            summary = "Buscar direccion por ID con HATEOAS",
            description = "Retorna una direccion por su identificador con enlaces HATEOAS"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Direccion encontrada",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = EntityModel.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Direccion no encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    public ResponseEntity<EntityModel<DireccionDTO>> findById(
            @Parameter(description = "ID de la direccion", example = "1", required = true)
            @PathVariable Integer id) {
        log.info("Solicitud V2 para buscar direccion por id: {}", id);
        DireccionDTO direccion = direccionService.findById(id);
        return ResponseEntity.ok(direccionModelAssembler.toModel(direccion));
    }

    @PostMapping("/direcciones")
    @Operation(
            summary = "Crear direccion con HATEOAS",
            description = "Registra una nueva direccion asociada a un cliente"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Direccion creada correctamente"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada invalidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cliente no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    public ResponseEntity<EntityModel<DireccionDTO>> save(@Valid @RequestBody DireccionRequestDTO request) {
        log.info("Solicitud V2 para crear direccion");
        DireccionDTO direccionCreada = direccionService.save(request);

        return ResponseEntity
                .created(URI.create("/api/v2/direcciones/" + direccionCreada.getId()))
                .body(direccionModelAssembler.toModel(direccionCreada));
    }

    @PutMapping("/direcciones/{id}")
    @Operation(
            summary = "Actualizar direccion con HATEOAS",
            description = "Actualiza una direccion existente y retorna el recurso con enlaces HATEOAS"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Direccion actualizada correctamente"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada invalidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Direccion o cliente no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    public ResponseEntity<EntityModel<DireccionDTO>> update(
            @Parameter(description = "ID de la direccion", example = "1", required = true)
            @PathVariable Integer id,
            @Valid @RequestBody DireccionRequestDTO request) {
        log.info("Solicitud V2 para actualizar direccion con id: {}", id);
        DireccionDTO direccionActualizada = direccionService.update(id, request);
        return ResponseEntity.ok(direccionModelAssembler.toModel(direccionActualizada));
    }

    @DeleteMapping("/direcciones/{id}")
    @Operation(
            summary = "Eliminar direccion",
            description = "Elimina una direccion por su identificador"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Direccion eliminada correctamente"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Direccion no encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID de la direccion", example = "1", required = true)
            @PathVariable Integer id) {
        log.info("Solicitud V2 para eliminar direccion con id: {}", id);
        direccionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
