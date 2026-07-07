package com.duoc.msempleados.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Representa la entidad empleado almacenada en la base de datos
 */
@Entity
@Table(name = "empleados")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidad que representa un empleado del sistema")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador unico del empleado", example = "1")
    private Integer id;

    @Column(nullable = false, length = 12, unique = true)
    @Schema(description = "Rut del empleado", example = "12345678-9")
    private String rut;

    @Column(nullable = false, length = 100)
    @Schema(description = "Nombre del empleado", example = "Carlos Perez")
    private String nombre;

    @Column(nullable = false, length = 100)
    @Schema(description = "Cargo del empleado", example = "Ejecutivo de arriendo")
    private String cargo;

    @Column(nullable = false, length = 100, unique = true)
    @Schema(description = "Correo electronico del empleado", example = "carlos.perez@empresa.cl")
    private String email;

    @Column(nullable = false)
    @Schema(description = "Sueldo del empleado", example = "750000")
    private BigDecimal sueldo;

    @Column(nullable = false)
    @Schema(description = "Estado activo del empleado", example = "true")
    private Boolean activo = true;

    @Column(nullable = false)
    @Schema(description = "Fecha de ingreso del empleado", example = "2024-03-10")
    private LocalDate fechaIngreso;
}
