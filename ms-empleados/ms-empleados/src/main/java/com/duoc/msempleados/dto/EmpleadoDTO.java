package com.duoc.msempleados.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Schema(description = "Datos de un empleado para respuestas de la API")
public class EmpleadoDTO {

    @Schema(description = "ID unico del empleado", example = "1")
    private Integer id;

    @Schema(description = "RUT del empleado", example = "12345678-9")
    private String rut;

    @Schema(description = "Nombre completo del empleado", example = "Juan Perez")
    private String nombre;

    @Schema(description = "Cargo del empleado", example = "Ejecutivo de arriendos")
    private String cargo;

    @Schema(description = "Correo electronico del empleado", example = "juan.perez@empresa.cl")
    private String email;

    @Schema(description = "Sueldo del empleado", example = "850000")
    private BigDecimal sueldo;

    @Schema(description = "Indica si el empleado esta activo", example = "true")
    private Boolean activo = true;

    @Schema(description = "Fecha de ingreso del empleado", example = "2024-03-15")
    private LocalDate fechaIngreso;
}