package com.duoc.msvehiculos.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Representa la entidad vehiculo almacenada en la base de datos.
 */
@Entity
@Table(name = "vehiculos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Entidad que representa un vehiculo del sistema")
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador unico del vehiculo", example = "1")
    private Integer id;

    @Column(nullable = false, unique = true, length = 10)
    @Schema(description = "Patente del vehiculo", example = "ABCD12")
    private String patente;

    @Column(nullable = false, length = 50)
    @Schema(description = "Marca del vehiculo", example = "Toyota")
    private String marca;

    @Column(nullable = false, length = 50)
    @Schema(description = "Modelo del vehiculo", example = "Corolla")
    private String modelo;

    @Column(nullable = false)
    @Schema(description = "Anio del vehiculo", example = "2022")
    private Integer anio;

    @Column(nullable = false, length = 30)
    @Schema(description = "Color del vehiculo", example = "Blanco")
    private String color;

    @Column(nullable = false)
    @Schema(description = "Precio diario de arriendo", example = "35000")
    private BigDecimal precioArriendoDiario;

    @Column(nullable = false)
    @Schema(description = "Kilometraje actual del vehiculo", example = "25000")
    private Integer kilometraje;

    @Column(nullable = false)
    @Schema(description = "Indica si el vehiculo esta disponible", example = "true")
    private Boolean disponible;

    @Column(nullable = false)
    @Schema(description = "Fecha de ingreso del vehiculo", example = "2024-06-01")
    private LocalDate fechaIngreso;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    @Schema(description = "Categoria asociada al vehiculo")
    private Categoria categoria;
}