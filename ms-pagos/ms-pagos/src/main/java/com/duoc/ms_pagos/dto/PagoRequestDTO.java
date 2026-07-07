package com.duoc.ms_pagos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos requeridos para crear o actualizar un pago")
public class PagoRequestDTO {

    @NotNull(message = "El id de la reserva es obligatorio")
    @Positive(message = "El id de la reserva debe ser positivo")
    @Schema(description = "ID de la reserva asociada", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer reservaId;

    @NotBlank(message = "El metodo de pago es obligatorio")
    @Size(min = 2, max = 50, message = "El metodo de pago debe tener entre 2 y 50 caracteres")
    @Schema(description = "Metodo de pago utilizado", example = "Tarjeta de credito", requiredMode = Schema.RequiredMode.REQUIRED)
    private String metodoPago;

    @DecimalMin(value = "1.0", message = "El monto debe ser mayor a 0")
    @Schema(description = "Monto pagado", example = "125000", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal monto;

    @NotBlank(message = "El codigo de transaccion es obligatorio")
    @Size(min = 3, max = 100, message = "El codigo de transaccion debe tener entre 3 y 100 caracteres")
    @Schema(description = "Codigo de transaccion del pago", example = "TX-2024-0001", requiredMode = Schema.RequiredMode.REQUIRED)
    private String codigoTransaccion;

    @Schema(description = "Indica si el pago fue realizado", example = "true")
    private boolean pagado;

    @NotNull(message = "La fecha de pago es obligatoria")
    @PastOrPresent(message = "La fecha de pago no puede ser futura")
    @Schema(description = "Fecha del pago", example = "2024-04-20", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate fechaPago;

    @Size(max = 150, message = "La observacion no puede superar los 150 caracteres")
    @Schema(description = "Observacion del pago", example = "Pago confirmado")
    private String observacion;
}