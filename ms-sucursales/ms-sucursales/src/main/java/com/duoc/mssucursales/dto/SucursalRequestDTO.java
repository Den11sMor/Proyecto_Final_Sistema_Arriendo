package com.duoc.mssucursales.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class SucursalRequestDTO {

    @NotBlank(message = "El nombre de la sucursal es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(min = 5, max = 150, message = "La dirección debe tener entre 5 y 150 caracteres")
    private String direccion;

    @NotBlank(message = "La comuna es obligatoria")
    @Size(min = 2, max = 100, message = "La comuna debe tener entre 2 y 20 caracteres")
    private String comuna;

    @NotNull(message = "El teléfono es obligatorio")
    @Min(value = 10000000, message = "El teléfono debe tener al menos 8 dígitos")
    private Integer telefono;

    @NotNull(message = "El estado operativo es obligatorio")
    private Boolean operativa;

    @NotNull(message = "La fecha de apertura es obligatoria")
    @PastOrPresent(message = "La fecha de apertura no puede ser futura")
    private LocalDate fechaApertura;

    @Positive(message = "El id de la región debe ser un número positivo")
    @NotNull(message = "El id de la región es obligatorio")
    private Integer regionId;

}
