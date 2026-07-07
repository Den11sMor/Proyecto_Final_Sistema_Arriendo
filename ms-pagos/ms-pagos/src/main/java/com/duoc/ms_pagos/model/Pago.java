package com.duoc.ms_pagos.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Representa la entidad pago almacenada en la base de datos
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pagos")
@Schema(description = "Entidad que representa un pago del sistema")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador unico del pago", example = "1")
    private Integer id;

    @Column(name = "reserva_id", nullable = false)
    @Schema(description = "ID de la reserva asociada", example = "10")
    private Integer reservaId;

    @Column(name = "metodo_pago", nullable = false, length = 50)
    @Schema(description = "Metodo de pago utilizado", example = "Tarjeta de credito")
    private String metodoPago;

    @Column(nullable = false, precision = 10, scale = 2)
    @Schema(description = "Monto pagado", example = "125000")
    private BigDecimal monto;

    @Column(name = "codigo_transaccion", nullable = false, length = 100)
    @Schema(description = "Codigo de transaccion del pago", example = "TX-2024-0001")
    private String codigoTransaccion;

    @Column(nullable = false)
    @Schema(description = "Indica si el pago fue realizado", example = "true")
    private boolean pagado;

    @Column(name = "fecha_pago", nullable = false)
    @Schema(description = "Fecha del pago", example = "2024-04-20")
    private LocalDate fechaPago;

    @Column(length = 150)
    @Schema(description = "Observacion del pago", example = "Pago confirmado")
    private String observacion;
}