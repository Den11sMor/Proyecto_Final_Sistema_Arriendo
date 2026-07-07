package com.duoc.msvehiculos.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa la entidad categoria almacenada en la base de datos.
 */
@Entity
@Table(name = "categorias")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Entidad que representa una categoria de vehiculo")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador unico de la categoria", example = "1")
    private Integer id;

    @Column(nullable = false, length = 100)
    @Schema(description = "Nombre de la categoria", example = "Sedan")
    private String nombre;

    @Column(nullable = false, length = 200)
    @Schema(description = "Descripcion de la categoria", example = "Vehiculos compactos para uso diario")
    private String descripcion;

    @Column(nullable = false)
    @Schema(description = "Tarifa base de la categoria", example = "25000")
    private BigDecimal tarifaBase;

    @Column(nullable = false)
    @Schema(description = "Capacidad de pasajeros", example = "5")
    private Integer capacidadPasajeros;

    @Column(nullable = false)
    @Schema(description = "Indica si la categoria esta activa", example = "true")
    private Boolean activa;

    @Column(nullable = false)
    @Schema(description = "Fecha de creacion de la categoria", example = "2024-06-01")
    private LocalDate fechaCreacion;

    @Builder.Default
    @OneToMany(mappedBy = "categoria")
    @Schema(description = "Vehiculos asociados a la categoria")
    private List<Vehiculo> vehiculos = new ArrayList<>();
}