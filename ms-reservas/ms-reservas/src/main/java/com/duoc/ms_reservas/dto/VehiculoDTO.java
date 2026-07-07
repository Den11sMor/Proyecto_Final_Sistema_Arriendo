package com.duoc.ms_reservas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos de vehiculo recibidos desde el microservicio de vehiculos")
public class VehiculoDTO {

    @Schema(description = "ID unico del vehiculo", example = "2")
    private Integer id;

    @Schema(description = "Patente del vehiculo", example = "ABCD12")
    private String patente;

    @Schema(description = "Marca del vehiculo", example = "Toyota")
    private String marca;

    @Schema(description = "Modelo del vehiculo", example = "Corolla")
    private String modelo;

    @Schema(description = "Ano del vehiculo", example = "2024")
    private Integer anio;

    @Schema(description = "Precio de arriendo diario", example = "30000")
    private BigDecimal precioArriendoDiario;

    @Schema(description = "Indica si el vehiculo esta disponible", example = "true")
    private Boolean disponible;

    @Schema(description = "Indica si el vehiculo esta activo", example = "true")
    private Boolean activo;

    @Schema(description = "Fecha de registro del vehiculo", example = "2026-06-01")
    private LocalDate fechaRegistro;
}