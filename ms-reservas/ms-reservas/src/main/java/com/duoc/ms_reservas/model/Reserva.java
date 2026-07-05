package com.duoc.ms_reservas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "reserva")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // agregado: id del cliente que viene desde ms-clientes
    @Column(nullable = false)
    private Integer clienteId;

    // agregado: id del vehículo que viene desde ms-vehiculos
    @Column(nullable = false)
    private Integer vehiculoId;

    // agregado: fecha en que comienza la reserva
    @Column(nullable = false)
    private LocalDate fechaInicio;

    // agregado: fecha en que termina la reserva
    @Column(nullable = false)
    private LocalDate fechaFin;

    // agregado: cantidad de días de la reserva
    @Column(nullable = false)
    private Integer cantidadDias;

    // agregado: monto total calculado o registrado para la reserva
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal montoTotal;

    // agregado: observación de la reserva
    @Column(nullable = false, length = 150)
    private String observacion;

    // agregado: indica si la reserva está activa
    @Column(nullable = false)
    private boolean activa;

    // agregado: relación ManyToOne con EstadoReserva
    @ManyToOne
    @JoinColumn(name = "estado_reserva_id", nullable = false)
    private EstadoReserva estadoReserva;

}
