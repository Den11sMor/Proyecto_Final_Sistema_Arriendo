package com.duoc.mssucursales.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class RegionDTO {

    private Integer id;
    private String nombre;
    private String codigo;
    private Integer numeroRegion;
    private String capitalRegional;
    private Boolean activa;
    private LocalDate fechaCreacion;

}
