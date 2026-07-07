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
@Schema(description = "Datos de reserva recibidos desde el microservicio de reservas")
public class ReservaDTO {

    @Schema(description = "ID unico de la reserva", example = "10")
    private Integer id;

    @Schema(description = "ID del cliente asociado", example = "3")
    private Integer clienteId;

    @Schema(description = "ID del vehiculo asociado", example = "5")
    private Integer vehiculoId;

    @Schema(description = "Fecha de inicio de la reserva", example = "2024-04-15")
    private LocalDate fechaInicio;

    @Schema(description = "Fecha de termino de la reserva", example = "2024-04-20")
    private LocalDate fechaFin;

    @Schema(description = "Cantidad de dias de la reserva", example = "5")
    private Integer cantidadDias;

    @Schema(description = "Monto total de la reserva", example = "125000")
    private BigDecimal montoTotal;

    @Schema(description = "Observacion de la reserva", example = "Reserva confirmada")
    private String observacion;

    @Schema(description = "Indica si la reserva esta activa", example = "true")
    private boolean activa;

    @Schema(description = "ID del estado de reserva", example = "1")
    private Integer estadoReservaId;

    @Schema(description = "Nombre del estado de reserva", example = "Confirmada")
    private String nombreEstadoReserva;
}
