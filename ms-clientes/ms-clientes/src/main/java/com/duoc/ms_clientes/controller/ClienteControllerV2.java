package com.duoc.ms_clientes.controller;

import com.duoc.ms_clientes.assemblers.ClienteModelAssembler;
import com.duoc.ms_clientes.dto.ClienteDTO;
import com.duoc.ms_clientes.dto.ClienteRequestDTO;
import com.duoc.ms_clientes.exception.ErrorResponse;
import com.duoc.ms_clientes.service.ClienteService;
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
@Tag(name = "Clientes V2", description = "Operaciones de clientes con respuestas HATEOAS")
public class ClienteControllerV2 {

    private final ClienteService clienteService;
    private final ClienteModelAssembler clienteModelAssembler;

    @GetMapping("/clientes")
    @Operation(
            summary = "Listar clientes con HATEOAS",
            description = "Retorna todos los clientes con enlaces HATEOAS"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Clientes obtenidos correctamente",
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
    public ResponseEntity<CollectionModel<EntityModel<ClienteDTO>>> findAll() {
        log.info("Solicitud V2 para listar clientes");
        List<EntityModel<ClienteDTO>> clientes = clienteService.findAll()
                .stream()
                .map(clienteModelAssembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(
                clientes,
                Link.of("/api/v2/clientes").withSelfRel()
        ));
    }

    @GetMapping("/clientes/{id}")
    @Operation(
            summary = "Buscar cliente por ID con HATEOAS",
            description = "Retorna un cliente por su identificador con enlaces HATEOAS"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Cliente encontrado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = EntityModel.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cliente no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    public ResponseEntity<EntityModel<ClienteDTO>> findById(
            @Parameter(description = "ID del cliente", example = "1", required = true)
            @PathVariable Integer id) {
        log.info("Solicitud V2 para buscar cliente por id: {}", id);
        ClienteDTO cliente = clienteService.findById(id);
        return ResponseEntity.ok(clienteModelAssembler.toModel(cliente));
    }

    @PostMapping("/clientes")
    @Operation(
            summary = "Crear cliente con HATEOAS",
            description = "Registra un nuevo cliente y retorna el recurso con enlaces HATEOAS"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Cliente creado correctamente",
                    content = @Content(schema = @Schema(implementation = EntityModel.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada invalidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    public ResponseEntity<EntityModel<ClienteDTO>> save(@Valid @RequestBody ClienteRequestDTO request) {
        log.info("Solicitud V2 para crear cliente");
        ClienteDTO clienteCreado = clienteService.save(request);

        return ResponseEntity
                .created(URI.create("/api/v2/clientes/" + clienteCreado.getId()))
                .body(clienteModelAssembler.toModel(clienteCreado));
    }

    @PutMapping("/clientes/{id}")
    @Operation(
            summary = "Actualizar cliente con HATEOAS",
            description = "Actualiza un cliente existente y retorna el recurso con enlaces HATEOAS"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente actualizado correctamente"),
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
    public ResponseEntity<EntityModel<ClienteDTO>> update(
            @Parameter(description = "ID del cliente", example = "1", required = true)
            @PathVariable Integer id,
            @Valid @RequestBody ClienteRequestDTO request) {
        log.info("Solicitud V2 para actualizar cliente con id: {}", id);
        ClienteDTO clienteActualizado = clienteService.update(id, request);
        return ResponseEntity.ok(clienteModelAssembler.toModel(clienteActualizado));
    }

    @DeleteMapping("/clientes/{id}")
    @Operation(
            summary = "Eliminar cliente",
            description = "Elimina un cliente por su identificador"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cliente eliminado correctamente"),
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
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID del cliente", example = "1", required = true)
            @PathVariable Integer id) {
        log.info("Solicitud V2 para eliminar cliente con id: {}", id);
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/clientes/buscar-email")
    @Operation(
            summary = "Buscar clientes por email con HATEOAS",
            description = "Retorna clientes cuyo email contenga el texto indicado"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Clientes encontrados"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Texto de busqueda invalido",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    public ResponseEntity<CollectionModel<EntityModel<ClienteDTO>>> buscarPorEmail(
            @Parameter(description = "Texto para buscar dentro del email", example = "correo", required = true)
            @RequestParam String texto) {
        log.info("Solicitud V2 para buscar clientes por email: {}", texto);
        List<EntityModel<ClienteDTO>> clientes = clienteService.buscarPorEmail(texto)
                .stream()
                .map(clienteModelAssembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(
                clientes,
                Link.of("/api/v2/clientes/buscar-email?texto=" + texto).withSelfRel(),
                Link.of("/api/v2/clientes").withRel("clientes")
        ));
    }
}
