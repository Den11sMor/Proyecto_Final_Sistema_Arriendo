package com.duoc.mssucursales.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos requeridos para crear o actualizar una sucursal")
public class SucursalRequestDTO {

    @NotBlank(message = "El nombre de la sucursal es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Schema(description = "Nombre de la sucursal", example = "Sucursal Santiago Centro", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;

    @NotBlank(message = "La direccion es obligatoria")
    @Size(min = 5, max = 150, message = "La direccion debe tener entre 5 y 150 caracteres")
    @Schema(description = "Direccion de la sucursal", example = "Avenida Libertador Bernardo O'Higgins 123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String direccion;

    @NotBlank(message = "La comuna es obligatoria")
    @Size(min = 2, max = 100, message = "La comuna debe tener entre 2 y 100 caracteres")
    @Schema(description = "Comuna de la sucursal", example = "Santiago", requiredMode = Schema.RequiredMode.REQUIRED)
    private String comuna;

    @NotNull(message = "El telefono es obligatorio")
    @Min(value = 10000000, message = "El telefono debe tener al menos 8 digitos")
    @Schema(description = "Telefono de la sucursal", example = "223456789", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer telefono;

    @NotNull(message = "El estado operativo es obligatorio")
    @Schema(description = "Indica si la sucursal esta operativa", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean operativa;

    @NotNull(message = "La fecha de apertura es obligatoria")
    @PastOrPresent(message = "La fecha de apertura no puede ser futura")
    @Schema(description = "Fecha de apertura de la sucursal", example = "2024-03-15", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate fechaApertura;

    @Positive(message = "El id de la region debe ser un numero positivo")
    @NotNull(message = "El id de la region es obligatorio")
    @Schema(description = "ID de la region asociada", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer regionId;
}