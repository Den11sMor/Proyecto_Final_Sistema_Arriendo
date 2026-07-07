package com.duoc.mssucursales.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos de una region para respuestas de la API")
public class RegionDTO {

    @Schema(description = "ID unico de la region", example = "1")
    private Integer id;

    @Schema(description = "Nombre de la region", example = "Region Metropolitana")
    private String nombre;

    @Schema(description = "Codigo de la region", example = "RM")
    private String codigo;

    @Schema(description = "Numero de la region", example = "13")
    private Integer numeroRegion;

    @Schema(description = "Capital regional", example = "Santiago")
    private String capitalRegional;

    @Schema(description = "Indica si la region esta activa", example = "true")
    private Boolean activa;

    @Schema(description = "Fecha de creacion de la region", example = "2024-01-10")
    private LocalDate fechaCreacion;
}
