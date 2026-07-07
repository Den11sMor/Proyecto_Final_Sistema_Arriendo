package com.duoc.ms_reservas.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Representa la entidad reserva almacenada en la base de datos
 */
@Entity
@Table(name = "reserva")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidad que representa una reserva del sistema")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador unico de la reserva", example = "1")
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "ID del cliente asociado a la reserva", example = "5")
    private Integer clienteId;

    @Column(nullable = false)
    @Schema(description = "ID del vehiculo asociado a la reserva", example = "3")
    private Integer vehiculoId;

    @Column(nullable = false)
    @Schema(description = "Fecha de inicio de la reserva", example = "2026-07-01")
    private LocalDate fechaInicio;

    @Column(nullable = false)
    @Schema(description = "Fecha de termino de la reserva", example = "2026-07-05")
    private LocalDate fechaFin;

    @Column(nullable = false)
    @Schema(description = "Cantidad de dias de la reserva", example = "4")
    private Integer cantidadDias;

    @Column(nullable = false, precision = 10, scale = 2)
    @Schema(description = "Monto total de la reserva", example = "120000")
    private BigDecimal montoTotal;

    @Column(nullable = false, length = 150)
    @Schema(description = "Observacion de la reserva", example = "Reserva para viaje")
    private String observacion;

    @Column(nullable = false)
    @Schema(description = "Indica si la reserva esta activa", example = "true")
    private boolean activa;

    @ManyToOne
    @JoinColumn(name = "estado_reserva_id", nullable = false)
    @Schema(description = "Estado asociado a la reserva")
    private EstadoReserva estadoReserva;
}