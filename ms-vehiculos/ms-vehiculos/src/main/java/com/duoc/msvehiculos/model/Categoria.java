package com.duoc.msvehiculos.model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categorias")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 200)
    private String descripcion;

    @Column(nullable = false)
    private BigDecimal tarifaBase;

    @Column(nullable = false)
    private Integer capacidadPasajeros;

    @Column(nullable = false)
    private Boolean activa;

    @Column(nullable = false)
    private LocalDate fechaCreacion;

    // agregado: una categoría puede tener muchos vehículos
    @Builder.Default
    @OneToMany(mappedBy = "categoria")
    private List<Vehiculo> vehiculos = new ArrayList<>();
}
