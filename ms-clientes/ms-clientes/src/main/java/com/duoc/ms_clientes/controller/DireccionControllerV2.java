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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Map<String, Object>> findAll() {
        log.info("Solicitud V2 para listar direcciones");
        List<Map<String, Object>> direcciones = direccionService.findAll()
                .stream()
                .map(direccionModelAssembler::toModel)
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(collection("direccionDTOList", direcciones, "/api/v2/direcciones"));
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
    public ResponseEntity<Map<String, Object>> findById(
            @Parameter(description = "ID de la direccion", example = "1", required = true)
            @PathVariable Integer id) {
        log.info("Solicitud V2 para buscar direccion por id: {}", id);
        DireccionDTO direccion = direccionService.findById(id);
        return ResponseEntity.ok(toResponse(direccionModelAssembler.toModel(direccion)));
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
    public ResponseEntity<Map<String, Object>> save(@Valid @RequestBody DireccionRequestDTO request) {
        log.info("Solicitud V2 para crear direccion");
        DireccionDTO direccionCreada = direccionService.save(request);

        return ResponseEntity
                .created(URI.create("/api/v2/direcciones/" + direccionCreada.getId()))
                .body(toResponse(direccionModelAssembler.toModel(direccionCreada)));
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
    public ResponseEntity<Map<String, Object>> update(
            @Parameter(description = "ID de la direccion", example = "1", required = true)
            @PathVariable Integer id,
            @Valid @RequestBody DireccionRequestDTO request) {
        log.info("Solicitud V2 para actualizar direccion con id: {}", id);
        DireccionDTO direccionActualizada = direccionService.update(id, request);
        return ResponseEntity.ok(toResponse(direccionModelAssembler.toModel(direccionActualizada)));
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

    private Map<String, Object> toResponse(EntityModel<DireccionDTO> entityModel) {
        DireccionDTO direccion = entityModel.getContent();
        Map<String, Object> model = new LinkedHashMap<>();
        if (direccion != null) {
            model.put("id", direccion.getId());
            model.put("calle", direccion.getCalle());
            model.put("numero", direccion.getNumero());
            model.put("comuna", direccion.getComuna());
            model.put("ciudad", direccion.getCiudad());
            model.put("referencia", direccion.getReferencia());
            model.put("principal", direccion.getPrincipal());
            model.put("fechaRegistro", direccion.getFechaRegistro());
            model.put("clienteId", direccion.getClienteId());
        }
        Map<String, Object> links = linksFrom(entityModel);
        model.put("_links", links);
        return model;
    }

    private Map<String, Object> linksFrom(EntityModel<?> entityModel) {
        Map<String, Object> links = new LinkedHashMap<>();
        for (Link link : entityModel.getLinks()) {
            links.put(link.getRel().value(), link(link.getHref()));
        }
        return links;
    }

    private Map<String, Object> collection(String name, List<Map<String, Object>> items, String selfHref) {
        Map<String, Object> response = new LinkedHashMap<>();
        Map<String, Object> embedded = new LinkedHashMap<>();
        embedded.put(name, items);
        response.put("_embedded", embedded);
        response.put("_links", Map.of("self", link(selfHref)));
        return response;
    }

    private Map<String, String> link(String href) {
        return Map.of("href", href);
    }
}
