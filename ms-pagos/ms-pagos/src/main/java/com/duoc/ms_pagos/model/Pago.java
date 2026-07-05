package com.duoc.ms_pagos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pagos")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "reserva_id", nullable = false)
    private Integer reservaId;

    @Column(name = "metodo_pago", nullable = false, length = 50)
    private String metodoPago;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    @Column(name = "codigo_transaccion", nullable = false, length = 100)
    private String codigoTransaccion;

    @Column(nullable = false)
    private boolean pagado;

    @Column(name = "fecha_pago", nullable = false)
    private LocalDate fechaPago;

    @Column(length = 150)
    private String observacion;
}
