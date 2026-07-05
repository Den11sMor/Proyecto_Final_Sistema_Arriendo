package com.duoc.ms_pagos.dto;

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
public class PagoRequestDTO {

    @NotNull(message = "El id de la reserva es obligatorio")
    @Positive(message = "El id de la reserva debe ser positivo")
    private Integer reservaId;

    @NotBlank(message = "El método de pago es obligatorio")
    @Size(min = 2, max = 50, message = "El método de pago debe tener entre 2 y 50 caracteres")
    private String metodoPago;

    @DecimalMin(value = "1.0", message = "El monto debe ser mayor a 0")
    private BigDecimal monto;

    @NotBlank(message = "El código de transacción es obligatorio")
    @Size(min = 3, max = 100, message = "El código de transacción debe tener entre 3 y 100 caracteres")
    private String codigoTransaccion;

    private boolean pagado;

    @NotNull(message = "La fecha de pago es obligatoria")
    @PastOrPresent(message = "La fecha de pago no puede ser futura")
    private LocalDate fechaPago;

    @Size(max = 150, message = "La observación no puede superar los 150 caracteres")
    private String observacion;
}
