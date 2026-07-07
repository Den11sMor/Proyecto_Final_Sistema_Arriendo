package com.duoc.ms_reportes.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos de un reporte para respuestas de la API")
public class ReporteDTO {

    @Schema(description = "ID unico del reporte", example = "1")
    private Integer id;

    @Schema(description = "ID de la reserva asociada", example = "10")
    private Integer reservaId;

    @Schema(description = "ID del pago asociado", example = "5")
    private Integer pagoId;

    @Schema(description = "Tipo de reporte", example = "RESUMEN_RESERVA")
    private String tipoReporte;

    @Schema(description = "Fecha de generacion del reporte", example = "2024-04-20")
    private LocalDate fechaGeneracion;

    @Schema(description = "Descripcion del reporte", example = "Reporte generado para reserva confirmada")
    private String descripcion;

    @Schema(description = "Total de la reserva", example = "125000")
    private BigDecimal totalReserva;

    @Schema(description = "Monto pagado", example = "125000")
    private BigDecimal montoPagado;

    @Schema(description = "Indica si la reserva esta activa", example = "true")
    private boolean reservaActiva;

    @Schema(description = "Indica si el pago esta confirmado", example = "true")
    private boolean pagoConfirmado;
}