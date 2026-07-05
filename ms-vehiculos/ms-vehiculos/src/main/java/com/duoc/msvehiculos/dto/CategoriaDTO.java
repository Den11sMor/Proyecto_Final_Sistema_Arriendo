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
@Schema(description = "Datos de una categoria para respuestas de la API")
public class CategoriaDTO {

    @Schema(description = "ID unico de la categoria", example = "1")
    private Integer id;

    @Schema(description = "Nombre de la categoria", example = "Sedan")
    private String nombre;

    @Schema(description = "Descripcion de la categoria", example = "Vehiculos compactos para uso diario")
    private String descripcion;

    @Schema(description = "Tarifa base de la categoria", example = "25000")
    private BigDecimal tarifaBase;

    @Schema(description = "Capacidad de pasajeros", example = "5")
    private Integer capacidadPasajeros;

    @Schema(description = "Indica si la categoria esta activa", example = "true")
    private Boolean activa;

    @Schema(description = "Fecha de creacion de la categoria", example = "2024-06-01")
    private LocalDate fechaCreacion;
}
