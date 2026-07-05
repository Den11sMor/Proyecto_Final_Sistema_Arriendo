package com.duoc.ms_clientes.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos de un cliente para respuestas de la API")
public class ClienteDTO {

    @Schema(description = "ID unico del cliente", example = "1")
    private Integer id;

    @Schema(description = "Rut del cliente", example = "12345678-9")
    private String rut;

    @Schema(description = "Nombre del cliente", example = "Juan")
    private String nombre;

    @Schema(description = "Apellido del cliente", example = "Perez")
    private String apellido;

    @Schema(description = "Email del cliente", example = "juan.perez@ejemplo.com")
    private String email;

    @Schema(description = "Telefono del cliente", example = "987654321")
    private Integer telefono;

    @Schema(description = "Estado del cliente", example = "true")
    private Boolean activo;

    @Schema(description = "Fecha de registro del cliente", example = "2024-06-01")
    private LocalDate fechaRegistro;
}
