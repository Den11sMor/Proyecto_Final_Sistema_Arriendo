package com.duoc.ms_reservas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "estado_reserva")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class EstadoReserva {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // agregado: nombre del estado, por ejemplo Pendiente, Confirmada o Cancelada
    @Column(nullable = false, length = 50)
    private String nombre;

    // agregado: descripción del significado del estado
    @Column(nullable = false, length = 150)
    private String descripcion;

    // agregado: número para ordenar o identificar importancia del estado
    @Column(nullable = false)
    private Integer prioridad;

    // agregado: indica si el estado se encuentra activo
    @Column(nullable = false)
    private boolean activo;

    // agregado: fecha de creación del estado
    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    // agregado: relación OneToMany solicitada en la pauta
    @OneToMany(mappedBy = "estadoReserva")
    private List<Reserva> reservas;

}
