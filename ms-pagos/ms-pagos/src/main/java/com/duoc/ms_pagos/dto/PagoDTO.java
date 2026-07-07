package com.duoc.ms_pagos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos de un pago para respuestas de la API")
public class PagoDTO {

    @Schema(description = "ID unico del pago", example = "1")
    private Integer id;

    @Schema(description = "ID de la reserva asociada", example = "10")
    private Integer reservaId;

    @Schema(description = "Metodo de pago utilizado", example = "Tarjeta de credito")
    private String metodoPago;

    @Schema(description = "Monto pagado", example = "125000")
    private BigDecimal monto;

    @Schema(description = "Codigo de transaccion del pago", example = "TX-2024-0001")
    private String codigoTransaccion;

    @Schema(description = "Indica si el pago fue realizado", example = "true")
    private boolean pagado;

    @Schema(description = "Fecha del pago", example = "2024-04-20")
    private LocalDate fechaPago;

    @Schema(description = "Observacion del pago", example = "Pago confirmado")
    private String observacion;
}