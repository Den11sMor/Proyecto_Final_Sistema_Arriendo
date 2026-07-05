package com.duoc.msvehiculos.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "vehiculos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 10)
    private String patente;

    @Column(nullable = false, length = 50)
    private String marca;

    @Column(nullable = false, length = 50)
    private String modelo;

    @Column(nullable = false)
    private Integer anio;

    @Column(nullable = false, length = 30)
    private String color;

    @Column(nullable = false)
    private BigDecimal precioArriendoDiario;

    @Column(nullable = false)
    private Integer kilometraje;

    @Column(nullable = false)
    private Boolean disponible;

    @Column(nullable = false)
    private LocalDate fechaIngreso;

    // agregado: cada vehículo pertenece a una categoría
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;
}
