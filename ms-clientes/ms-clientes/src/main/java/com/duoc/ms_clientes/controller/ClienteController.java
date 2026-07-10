package com.duoc.ms_clientes.controller;

import com.duoc.ms_clientes.dto.ClienteDTO;
import com.duoc.ms_clientes.dto.ClienteRequestDTO;
import com.duoc.ms_clientes.exception.ErrorResponse;
import com.duoc.ms_clientes.service.ClienteService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Clientes", description = "Operaciones CRUD y busqueda de clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping("/clientes")
    @Operation(
            summary = "Listar clientes",
            description = "Retorna todos los clientes registrados en el sistema"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Clientes obtenidos correctamente",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ClienteDTO.class))
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<ClienteDTO>> findAll() {
        log.info("Solicitud para listar clientes");
        return ResponseEntity.ok(clienteService.findAll());
    }
    @GetMapping("/clientes/{id}")
    @Operation(
            summary = "Buscar cliente por ID",
            description = "Retorna un cliente segun su identificador"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Cliente encontrado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ClienteDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cliente no encontrado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "timestamp": "2026-07-07T10:30:00",
                                      "status": 404,
                                      "error": "Not Found",
                                      "message": "Cliente no encontrado con id: 99",
                                      "path": "/api/v1/clientes/99"
                                    }
                                    """)
                    )
            )
    })
    public ResponseEntity<ClienteDTO> findById(
            @Parameter(description = "ID del cliente", example = "1", required = true)
            @PathVariable Integer id) {
        log.info("Solicitud para buscar cliente por id: {}", id);
        return ResponseEntity.ok(clienteService.findById(id));
    }

    @PostMapping("/clientes")
    @Operation(
            summary = "Crear cliente",
            description = "Registra un nuevo cliente en el sistema"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Cliente creado correctamente",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ClienteDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ClienteDTO> save(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos requeridos para crear un cliente",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ClienteRequestDTO.class),
                            examples = @ExampleObject(
                                    name = "Cliente",
                                    value = """
                                            {
                                              "rut": "12345678-9",
                                              "nombre": "Carlos",
                                              "apellido": "Perez",
                                              "email": "carlos.perez@correo.cl",
                                              "telefono": "987654321",
                                              "activo": true
                                            }
                                            """
                            )
                    )
            )
            @Valid @RequestBody ClienteRequestDTO request) {
        log.info("Solicitud para crear cliente");
        ClienteDTO clienteCreado = clienteService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteCreado);
    }

    @PutMapping("/clientes/{id}")
    @Operation(
            summary = "Actualizar cliente",
            description = "Actualiza los datos de un cliente existente"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Cliente actualizado correctamente",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ClienteDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ClienteDTO> update(
            @Parameter(description = "ID del cliente", example = "1", required = true)
            @PathVariable Integer id,
            @Valid @RequestBody ClienteRequestDTO request) {

        log.info("Solicitud para actualizar cliente con id: {}", id);
        ClienteDTO clienteActualizado = clienteService.update(id, request);
        return ResponseEntity.ok(clienteActualizado);
    }

    @DeleteMapping("/clientes/{id}")
    @Operation(
            summary = "Eliminar cliente",
            description = "Elimina un cliente segun su identificador"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cliente eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID del cliente", example = "1", required = true)
            @PathVariable Integer id) {
        log.info("Solicitud para eliminar cliente con id: {}", id);
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/clientes/buscar-email")
    @Operation(
            summary = "Buscar clientes por email",
            description = "Retorna clientes cuyo email contenga el texto indicado"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Clientes encontrados",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ClienteDTO.class))
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Texto de busqueda invalido"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<ClienteDTO>> buscarPorEmail(
            @Parameter(description = "Texto para buscar dentro del email", example = "correo", required = true)
            @RequestParam String texto) {
        log.info("Solicitud para buscar clientes por email: {}", texto);
        return ResponseEntity.ok(clienteService.buscarPorEmail(texto));
    }
}
