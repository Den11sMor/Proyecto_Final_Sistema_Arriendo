package com.duoc.ms_reservas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehiculoDTO {


    //cambiar porque todavia no esta listo vehiculo
    private Integer id;
    private String patente;
    private String marca;
    private String modelo;
    private Integer anio;
    private BigDecimal precioArriendoDiario;
    private Boolean disponible;
    private Boolean activo;
    private LocalDate fechaRegistro;

}
