package com.duoc.msempleados.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class EmpleadoRequestDTO {

    @NotBlank(message = "El RUT es obligatorio")
    @Size(min = 8, max = 12, message = "El RUT debe tener entre 8 y 12 caracteres")
    private String rut;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "El cargo es obligatorio")
    @Size(min = 2, max = 100, message = "El cargo debe tener entre 2 y 100 caracteres")
    private String cargo;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe tener un formato válido")
    @Size(max = 100, message = "El email no puede superar los 100 caracteres")
    private String email;

    @NotNull(message = "El sueldo es obligatorio")
    @Positive(message = "El sueldo debe ser positivo")
    private BigDecimal sueldo;

    @NotNull(message = "El estado es obligatorio")
    private Boolean activo = true;

    @NotNull(message = "La fecha de ingreso es obligatoria")
    @PastOrPresent(message = "La fecha de ingreso no puede ser futura")
    private LocalDate fechaIngreso;


}
