package com.duoc.ms_clientes.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos necesarios para crear o actualizar un cliente")
public class ClienteRequestDTO {

    @Schema(description = "Rut del cliente", example = "12345678-9")
    @NotBlank
    @Size(min = 9, max = 12)
    private String rut;

    @Schema(description = "Nombre del cliente", example = "Juan")
    @NotBlank
    @Size(min = 2, max = 100)
    private String nombre;

    @Schema(description = "Apellido del cliente", example = "Perez")
    @NotBlank
    @Size(min = 2, max = 100)
    private String apellido;

    @Schema(description = "Email del cliente", example = "juan.perez@gmail.com")
    @NotBlank
    @Email
    @Size(min = 2, max = 100)
    private String email;

    @Schema(description = "Telefono del cliente", example = "987654321")
    @NotNull
    @Positive
    private Integer telefono;

    @Schema(description = "Indica si el cliente esta activo", example = "true")
    @NotNull
    private Boolean activo;

    @Schema(description = "Fecha de registro del cliente", example = "2024-06-01")
    @NotNull
    @PastOrPresent
    private LocalDate fechaRegistro;
}
