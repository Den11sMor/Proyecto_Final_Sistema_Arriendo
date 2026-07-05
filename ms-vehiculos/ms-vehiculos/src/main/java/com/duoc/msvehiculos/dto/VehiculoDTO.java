package com.duoc.msvehiculos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Datos de un vehiculo para respuestas de la API")
public class VehiculoDTO {

    @Schema(description = "ID unico del vehiculo", example = "1")
    private Integer id;

    @Schema(description = "Patente del vehiculo", example = "ABCD12")
    private String patente;

    @Schema(description = "Marca del vehiculo", example = "Toyota")
    private String marca;

    @Schema(description = "Modelo del vehiculo", example = "Corolla")
    private String modelo;

    @Schema(description = "Anio del vehiculo", example = "2022")
    private Integer anio;

    @Schema(description = "Color del vehiculo", example = "Blanco")
    private String color;

    @Schema(description = "Precio diario de arriendo", example = "35000")
    private BigDecimal precioArriendoDiario;

    @Schema(description = "Kilometraje actual del vehiculo", example = "25000")
    private Integer kilometraje;

    @Schema(description = "Indica si el vehiculo esta disponible", example = "true")
    private Boolean disponible;

    @Schema(description = "Fecha de ingreso del vehiculo", example = "2024-06-01")
    private LocalDate fechaIngreso;

    @Schema(description = "ID de la categoria asociada", example = "1")
    private Integer categoriaId;

    @Schema(description = "Nombre de la categoria asociada", example = "Sedan")
    private String categoriaNombre;
}
