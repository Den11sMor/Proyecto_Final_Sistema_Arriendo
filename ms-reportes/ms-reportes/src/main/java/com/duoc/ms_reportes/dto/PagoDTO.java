package com.duoc.ms_reportes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoDTO {

    private Integer id;
    private Integer reservaId;
    private String metodoPago;
    private BigDecimal monto;
    private String codigoTransaccion;
    private boolean pagado;
    private LocalDate fechaPago;
    private String observacion;
}
