package com.duoc.ms_reportes.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Representa la entidad reporte almacenada en la base de datos
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reportes")
@Schema(description = "Entidad que representa un reporte del sistema")
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador unico del reporte", example = "1")
    private Integer id;

    @Column(name = "reserva_id", nullable = false)
    @Schema(description = "ID de la reserva asociada", example = "10")
    private Integer reservaId;

    @Column(name = "pago_id", nullable = false)
    @Schema(description = "ID del pago asociado", example = "5")
    private Integer pagoId;

    @Column(name = "tipo_reporte", nullable = false, length = 50)
    @Schema(description = "Tipo de reporte", example = "RESUMEN_RESERVA")
    private String tipoReporte;

    @Column(name = "fecha_generacion", nullable = false)
    @Schema(description = "Fecha de generacion del reporte", example = "2024-04-20")
    private LocalDate fechaGeneracion;

    @Column(length = 200)
    @Schema(description = "Descripcion del reporte", example = "Reporte generado para reserva confirmada")
    private String descripcion;

    @Column(name = "total_reserva", nullable = false, precision = 10, scale = 2)
    @Schema(description = "Total de la reserva", example = "125000")
    private BigDecimal totalReserva;

    @Column(name = "monto_pagado", nullable = false, precision = 10, scale = 2)
    @Schema(description = "Monto pagado", example = "125000")
    private BigDecimal montoPagado;

    @Column(name = "reserva_activa", nullable = false)
    @Schema(description = "Indica si la reserva esta activa", example = "true")
    private boolean reservaActiva;

    @Column(name = "pago_confirmado", nullable = false)
    @Schema(description = "Indica si el pago esta confirmado", example = "true")
    private boolean pagoConfirmado;
}