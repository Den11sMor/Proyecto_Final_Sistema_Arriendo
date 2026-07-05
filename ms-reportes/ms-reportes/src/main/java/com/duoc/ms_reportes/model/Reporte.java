package com.duoc.ms_reportes.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reportes")
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "reserva_id", nullable = false)
    private Integer reservaId;

    @Column(name = "pago_id", nullable = false)
    private Integer pagoId;

    @Column(name = "tipo_reporte", nullable = false, length = 50)
    private String tipoReporte;

    @Column(name = "fecha_generacion", nullable = false)
    private LocalDate fechaGeneracion;

    @Column(length = 200)
    private String descripcion;

    @Column(name = "total_reserva", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalReserva;

    @Column(name = "monto_pagado", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoPagado;

    @Column(name = "reserva_activa", nullable = false)
    private boolean reservaActiva;

    @Column(name = "pago_confirmado", nullable = false)
    private boolean pagoConfirmado;
}
