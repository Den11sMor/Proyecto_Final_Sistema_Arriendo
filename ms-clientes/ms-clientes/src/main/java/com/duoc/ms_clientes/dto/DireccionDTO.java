package com.duoc.ms_clientes.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos de una direccion para respuestas de la API")
public class DireccionDTO {

    @Schema(description = "ID unico de la direccion", example = "1")
    private Integer id;

    @Schema(description = "Calle de la direccion", example = "Av Siempre Viva")
    private String calle;

    @Schema(description = "Numero de la direccion", example = "742")
    private Integer numero;

    @Schema(description = "Comuna de la direccion", example = "Santiago")
    private String comuna;

    @Schema(description = "Ciudad de la direccion", example = "Santiago")
    private String ciudad;

    @Schema(description = "Referencia de la direccion", example = "Casa azul")
    private String referencia;

    @Schema(description = "Indica si es la direccion principal", example = "true")
    private Boolean principal;

    @Schema(description = "Fecha de registro de la direccion", example = "2024-06-01")
    private LocalDate fechaRegistro;

    @Schema(description = "ID del cliente asociado", example = "1")
    private Integer clienteId;
}
