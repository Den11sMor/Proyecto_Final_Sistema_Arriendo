package com.duoc.mssucursales.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "Datos requeridos para crear o actualizar una region")
public class RegionRequestDTO {

    @NotBlank(message = "El nombre de la region es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Schema(description = "Nombre de la region", example = "Region Metropolitana", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;

    @NotBlank(message = "El codigo de la region es obligatorio")
    @Size(min = 2, max = 20, message = "El codigo debe tener entre 2 y 20 caracteres")
    @Schema(description = "Codigo de la region", example = "RM", requiredMode = Schema.RequiredMode.REQUIRED)
    private String codigo;

    @NotNull(message = "El numero de region es obligatorio")
    @Min(value = 1, message = "El numero de region debe ser mayor o igual a 1")
    @Max(value = 16, message = "El numero de region no puede ser mayor a 16")
    @Schema(description = "Numero de la region", example = "13", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer numeroRegion;

    @NotBlank(message = "La capital regional es obligatoria")
    @Size(min = 2, max = 100, message = "La capital regional debe tener entre 2 y 100 caracteres")
    @Schema(description = "Capital regional", example = "Santiago", requiredMode = Schema.RequiredMode.REQUIRED)
    private String capitalRegional;

    @NotNull(message = "El estado activo es obligatorio")
    @Schema(description = "Indica si la region esta activa", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean activa;

    @NotNull(message = "La fecha de creacion es obligatoria")
    @PastOrPresent(message = "La fecha de creacion no puede ser futura")
    @Schema(description = "Fecha de creacion de la region", example = "2024-01-10", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate fechaCreacion;
}

