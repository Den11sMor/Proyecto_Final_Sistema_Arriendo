package com.duoc.mssucursales.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Representa la entidad sucursal almacenada en la base de datos
 */
@Entity
@Table(name = "sucursales")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidad que representa una sucursal del sistema")
public class Sucursal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador unico de la sucursal", example = "1")
    private Integer id;

    @Column(nullable = false, length = 100)
    @Schema(description = "Nombre de la sucursal", example = "Sucursal Santiago Centro")
    private String nombre;

    @Column(nullable = false, length = 150)
    @Schema(description = "Direccion de la sucursal", example = "Av Libertador Bernardo OHiggins 1234")
    private String direccion;

    @Column(nullable = false, length = 100)
    @Schema(description = "Comuna de la sucursal", example = "Santiago")
    private String comuna;

    @Column(nullable = false)
    @Schema(description = "Telefono de la sucursal", example = "221234567")
    private Integer telefono;

    @Column(nullable = false)
    @Schema(description = "Indica si la sucursal esta operativa", example = "true")
    private Boolean operativa = true;

    @Column(nullable = false)
    @Schema(description = "Fecha de apertura de la sucursal", example = "2024-03-15")
    private LocalDate fechaApertura;

    @ManyToOne
    @JoinColumn(name = "region_id", nullable = false)
    @Schema(description = "Region asociada a la sucursal")
    private Region region;
}