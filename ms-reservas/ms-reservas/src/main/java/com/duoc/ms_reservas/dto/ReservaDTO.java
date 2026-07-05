package com.duoc.ms_reservas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true) // Necesario por heredar de RepresentationModel
@Schema(description = "Datos de una reserva devueltos por la API")
public class ReservaDTO extends RepresentationModel<ReservaDTO> {

    @Schema(description = "ID único de la reserva", example = "1")
    private Integer id;
    @Schema(description = "ID del cliente que realiza la reserva", example = "1")
    private Integer clienteId;
    @Schema(description = "ID del vehículo reservado", example = "2")
    private Integer vehiculoId;
    @Schema(description = "Fecha de inicio de la reserva", example = "2026-07-01")
    private LocalDate fechaInicio;
    @Schema(description = "Fecha de término de la reserva", example = "2026-07-05")
    private LocalDate fechaFin;
    @Schema(description = "Cantidad total de días reservados", example = "4")
    private Integer cantidadDias;
    @Schema(description = "Monto total de la reserva", example = "120000")
    private BigDecimal montoTotal;
    @Schema(description = "Observación asociada a la reserva", example = "Reserva para viaje de negocios")
    private String observacion;
    @Schema(description = "Indica si la reserva está activa", example = "true")
    private boolean activa;
    @Schema(description = "ID del estado de la reserva", example = "1")
    private Integer estadoReservaId;
    @Schema(description = "Nombre legible del estado de reserva", example = "Pendiente")
    private String nombreEstadoReserva;

}
