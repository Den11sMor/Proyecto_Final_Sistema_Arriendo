package com.duoc.ms_reportes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteDTO {

    private Integer id;
    private Integer reservaId;
    private Integer pagoId;
    private String tipoReporte;
    private LocalDate fechaGeneracion;
    private String descripcion;
    private BigDecimal totalReserva;
    private BigDecimal montoPagado;
    private boolean reservaActiva;
    private boolean pagoConfirmado;
}
