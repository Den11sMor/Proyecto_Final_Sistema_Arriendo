package com.duoc.msempleados.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Schema(description = "Datos requeridos para crear o actualizar un empleado")
public class EmpleadoRequestDTO {

    @NotBlank(message = "El RUT es obligatorio")
    @Size(min = 8, max = 12, message = "El RUT debe tener entre 8 y 12 caracteres")
    @Schema(description = "RUT del empleado", example = "12345678-9", requiredMode = Schema.RequiredMode.REQUIRED)
    private String rut;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Schema(description = "Nombre completo del empleado", example = "Juan Perez", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;

    @NotBlank(message = "El cargo es obligatorio")
    @Size(min = 2, max = 100, message = "El cargo debe tener entre 2 y 100 caracteres")
    @Schema(description = "Cargo del empleado", example = "Ejecutivo de arriendos", requiredMode = Schema.RequiredMode.REQUIRED)
    private String cargo;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe tener un formato valido")
    @Size(max = 100, message = "El email no puede superar los 100 caracteres")
    @Schema(description = "Correo electronico del empleado", example = "juan.perez@empresa.cl", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @NotNull(message = "El sueldo es obligatorio")
    @Positive(message = "El sueldo debe ser positivo")
    @Schema(description = "Sueldo del empleado", example = "850000", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal sueldo;

    @NotNull(message = "El estado es obligatorio")
    @Schema(description = "Indica si el empleado esta activo", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean activo = true;

    @NotNull(message = "La fecha de ingreso es obligatoria")
    @PastOrPresent(message = "La fecha de ingreso no puede ser futura")
    @Schema(description = "Fecha de ingreso del empleado", example = "2024-03-15", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate fechaIngreso;
}