package com.duoc.ms_reservas.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Representa la entidad estado de reserva almacenada en la base de datos
 */
@Entity
@Table(name = "estado_reserva")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidad que representa un estado de reserva del sistema")
public class EstadoReserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador unico del estado de reserva", example = "1")
    private Integer id;

    @Column(nullable = false, length = 50)
    @Schema(description = "Nombre del estado de reserva", example = "Confirmada")
    private String nombre;

    @Column(nullable = false, length = 150)
    @Schema(description = "Descripcion del estado de reserva", example = "Reserva confirmada por el sistema")
    private String descripcion;

    @Column(nullable = false)
    @Schema(description = "Prioridad del estado de reserva", example = "1")
    private Integer prioridad;

    @Column(nullable = false)
    @Schema(description = "Indica si el estado de reserva esta activo", example = "true")
    private boolean activo;

    @Column(nullable = false)
    @Schema(description = "Fecha de creacion del estado de reserva", example = "2026-07-05T10:30:00")
    private LocalDateTime fechaCreacion;

    @OneToMany(mappedBy = "estadoReserva")
    @Schema(description = "Reservas asociadas a este estado")
    private List<Reserva> reservas;
}