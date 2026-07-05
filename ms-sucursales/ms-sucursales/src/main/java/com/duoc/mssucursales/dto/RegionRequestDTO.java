package com.duoc.mssucursales.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data

public class RegionRequestDTO {
    @NotBlank(message = "El nombre de la region es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "El código de la región es obligatorio")
    @Size(min = 2, max = 20, message = "El código debe tener entre 2 y 20 caracteres")
    private String codigo;

    @NotNull(message = "El número de región es obligatorio")
    @Min(value = 1, message = "El número de región debe ser mayor o igual a 1")
    @Max(value = 16, message = "El número de región no puede ser mayor a 16")
    private Integer numeroRegion;

    @NotBlank(message = "La capital regional es obligatoria")
    @Size(min = 2, max = 100, message = "La capital regional debe tener entre 2 y 100 caracteres")
    private String capitalRegional;

    @NotNull(message = "El estado activo es obligatorio")
    private Boolean activa;

    @NotNull(message = "La fecha de creación es obligatoria")
    @PastOrPresent(message = "La fecha de creación no puede ser futura")
    private LocalDate fechaCreacion;
}


