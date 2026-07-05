package com.duoc.mssucursales.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "regiones")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 20, unique = true)
    private String codigo;

    @Column(nullable = false)
    private Integer numeroRegion;

    @Column(nullable = false, length = 100)
    private String capitalRegional;

    @Column(nullable = false)
    private Boolean activa = true;

    @Column(nullable = false)
    private LocalDate fechaCreacion;

    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sucursal> sucursales;
}
