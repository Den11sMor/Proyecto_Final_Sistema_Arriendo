package com.duoc.mssucursales.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * Representa la entidad region almacenada en la base de datos
 */
@Entity
@Table(name = "regiones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidad que representa una region del sistema")
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador unico de la region", example = "1")
    private Integer id;

    @Column(nullable = false, length = 100)
    @Schema(description = "Nombre de la region", example = "Region Metropolitana")
    private String nombre;

    @Column(nullable = false, length = 20, unique = true)
    @Schema(description = "Codigo de la region", example = "RM")
    private String codigo;

    @Column(nullable = false)
    @Schema(description = "Numero de la region", example = "13")
    private Integer numeroRegion;

    @Column(nullable = false, length = 100)
    @Schema(description = "Capital regional", example = "Santiago")
    private String capitalRegional;

    @Column(nullable = false)
    @Schema(description = "Indica si la region esta activa", example = "true")
    private Boolean activa = true;

    @Column(nullable = false)
    @Schema(description = "Fecha de creacion de la region", example = "2024-01-10")
    private LocalDate fechaCreacion;

    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Sucursales asociadas a la region")
    private List<Sucursal> sucursales;
}