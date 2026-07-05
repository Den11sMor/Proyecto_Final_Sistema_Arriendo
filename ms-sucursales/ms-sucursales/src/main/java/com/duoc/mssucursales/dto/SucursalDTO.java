package com.duoc.mssucursales.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class SucursalDTO {

    private Integer id;
    private String nombre;
    private String direccion;
    private String comuna;
    private Integer telefono;
    private Boolean operativa;
    private LocalDate fechaApertura;

    private Integer regionId;
    private String nombreRegion;

}
