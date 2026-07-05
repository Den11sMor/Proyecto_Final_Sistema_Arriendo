package com.duoc.msempleados.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "empleados")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 12, unique = true)
    private String rut;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String cargo;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(nullable = false)
    private BigDecimal sueldo;

    @Column(nullable = false)
    private Boolean activo = true;

    @Column(nullable = false)
    private LocalDate fechaIngreso;



}
