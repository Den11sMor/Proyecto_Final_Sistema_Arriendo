package com.duoc.mssucursales.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "sucursales")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Sucursal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 150)
    private String direccion;

    @Column(nullable = false, length = 100)
    private String comuna;

    @Column(nullable = false)
    private Integer telefono;

    @Column(nullable = false)
    private Boolean operativa = true;

    @Column(nullable = false)
    private LocalDate fechaApertura;

    @ManyToOne
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;
}
