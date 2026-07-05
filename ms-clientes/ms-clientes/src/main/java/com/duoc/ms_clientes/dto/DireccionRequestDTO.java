package com.duoc.ms_clientes.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Datos necesarios para crear o actualizar una direccion")
public class DireccionRequestDTO {

    @Schema(description = "Calle de la direccion", example = "Av Siempre Viva")
    @NotBlank
    @Size(min = 2, max = 100)
    private String calle;

    @Schema(description = "Numero de la direccion", example = "742")
    @NotNull
    @Positive
    private Integer numero;

    @Schema(description = "Comuna de la direccion", example = "Santiago")
    @NotBlank
    @Size(min = 2, max = 100)
    private String comuna;

    @Schema(description = "Ciudad de la direccion", example = "Santiago")
    @NotBlank
    @Size(min = 2, max = 150)
    private String ciudad;

    @Schema(description = "Referencia de la direccion", example = "Casa azul")
    @NotBlank
    @Size(min = 2, max = 150)
    private String referencia;

    @Schema(description = "Indica si es la direccion principal", example = "true")
    @NotNull
    private Boolean principal;

    @Schema(description = "Fecha de registro de la direccion", example = "2024-06-01")
    @NotNull
    @PastOrPresent
    private LocalDate fechaRegistro;

    @Schema(description = "ID del cliente asociado", example = "1")
    @NotNull
    @Positive
    private Integer clienteId;
}
