package com.duoc.ms_clientes.controller;

import com.duoc.ms_clientes.dto.DireccionDTO;
import com.duoc.ms_clientes.dto.DireccionRequestDTO;
import com.duoc.ms_clientes.service.DireccionService;
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
@Tag(name = "Direcciones", description = "Operaciones CRUD de direcciones de clientes")
public class DireccionController {

    private final DireccionService direccionService;

    @GetMapping("/direcciones")
    @Operation(summary = "Listar direcciones", description = "Retorna todas las direcciones registradas")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Direcciones obtenidas correctamente",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = DireccionDTO.class))
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<DireccionDTO>> findAll() {
        return ResponseEntity.ok(direccionService.findAll());
    }

    @GetMapping("/direcciones/{id}")
    @Operation(summary = "Buscar direccion por ID", description = "Retorna una direccion segun su identificador")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Direccion encontrada",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DireccionDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Direccion no encontrada",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = "{\"mensaje\":\"Direccion no encontrada con id: 99\"}")
                    )
            )
    })
    public ResponseEntity<DireccionDTO> findById(
            @Parameter(description = "ID de la direccion", example = "1", required = true)
            @PathVariable Integer id) {
        return ResponseEntity.ok(direccionService.findById(id));
    }

    @PostMapping("/direcciones")
    @Operation(summary = "Crear direccion", description = "Registra una nueva direccion asociada a un cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Direccion creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<DireccionDTO> save(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos requeridos para crear una direccion",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DireccionRequestDTO.class),
                            examples = @ExampleObject(
                                    name = "Direccion",
                                    value = """
                                            {
                                              "calle": "Av Siempre Viva",
                                              "numero": 742,
                                              "comuna": "Santiago",
                                              "ciudad": "Santiago",
                                              "referencia": "Casa azul",
                                              "principal": true,
                                              "fechaRegistro": "2024-06-01",
                                              "clienteId": 1
                                            }
                                            """
                            )
                    )
            )
            @Valid @RequestBody DireccionRequestDTO request) {
        DireccionDTO direccionCreada = direccionService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(direccionCreada);
    }

    @PutMapping("/direcciones/{id}")
    @Operation(summary = "Actualizar direccion", description = "Actualiza una direccion existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Direccion actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos"),
            @ApiResponse(responseCode = "404", description = "Direccion o cliente no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<DireccionDTO> update(
            @Parameter(description = "ID de la direccion", example = "1", required = true)
            @PathVariable Integer id,
            @Valid @RequestBody DireccionRequestDTO request) {
        DireccionDTO direccionActualizada = direccionService.update(id, request);
        return ResponseEntity.ok(direccionActualizada);
    }

    @DeleteMapping("/direcciones/{id}")
    @Operation(summary = "Eliminar direccion", description = "Elimina una direccion segun su identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Direccion eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Direccion no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID de la direccion", example = "1", required = true)
            @PathVariable Integer id) {
        direccionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}