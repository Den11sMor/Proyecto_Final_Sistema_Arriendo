package com.duoc.ms_reservas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos de cliente recibidos desde el microservicio de clientes")
public class ClienteDTO {

    @Schema(description = "ID unico del cliente", example = "1")
    private Integer id;

    @Schema(description = "Rut del cliente", example = "12345678-9")
    private String rut;

    @Schema(description = "Nombre del cliente", example = "Carlos")
    private String nombre;

    @Schema(description = "Apellido del cliente", example = "Perez")
    private String apellido;

    @Schema(description = "Correo electronico del cliente", example = "carlos.perez@correo.cl")
    private String email;

    @Schema(description = "Telefono del cliente", example = "987654321")
    private Integer telefono;

    @Schema(description = "Indica si el cliente esta activo", example = "true")
    private Boolean activo;

    @Schema(description = "Fecha de registro del cliente", example = "2026-06-01")
    private LocalDate fechaRegistro;
}
