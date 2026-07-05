package com.duoc.ms_pagos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaDTO {

    private Integer id;
    private Integer clienteId;
    private Integer vehiculoId;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Integer cantidadDias;
    private BigDecimal montoTotal;
    private String observacion;
    private boolean activa;
    private Integer estadoReservaId;
    private String nombreEstadoReserva;
}
