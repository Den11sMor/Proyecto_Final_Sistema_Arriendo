package com.duoc.mssucursales.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos de una sucursal para respuestas de la API")
public class SucursalDTO {

    @Schema(description = "ID unico de la sucursal", example = "1")
    private Integer id;

    @Schema(description = "Nombre de la sucursal", example = "Sucursal Santiago Centro")
    private String nombre;

    @Schema(description = "Direccion de la sucursal", example = "Avenida Libertador Bernardo O'Higgins 123")
    private String direccion;

    @Schema(description = "Comuna de la sucursal", example = "Santiago")
    private String comuna;

    @Schema(description = "Telefono de la sucursal", example = "223456789")
    private Integer telefono;

    @Schema(description = "Indica si la sucursal esta operativa", example = "true")
    private Boolean operativa;

    @Schema(description = "Fecha de apertura de la sucursal", example = "2024-03-15")
    private LocalDate fechaApertura;

    @Schema(description = "ID de la region asociada", example = "1")
    private Integer regionId;

    @Schema(description = "Nombre de la region asociada", example = "Region Metropolitana")
    private String nombreRegion;
}