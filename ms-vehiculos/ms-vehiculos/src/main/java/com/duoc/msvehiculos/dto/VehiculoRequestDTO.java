package com.duoc.msvehiculos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Datos necesarios para crear o actualizar un vehiculo")
public class VehiculoRequestDTO {

    @Schema(description = "Patente del vehiculo", example = "ABCD12")
    @NotBlank(message = "La patente es obligatoria")
    @Size(min = 6, max = 10, message = "La patente debe tener entre 6 y 10 caracteres")
    private String patente;

    @Schema(description = "Marca del vehiculo", example = "Toyota")
    @NotBlank(message = "La marca es obligatoria")
    @Size(min = 2, max = 50, message = "La marca debe tener entre 2 y 50 caracteres")
    private String marca;

    @Schema(description = "Modelo del vehiculo", example = "Corolla")
    @NotBlank(message = "El modelo es obligatorio")
    @Size(min = 2, max = 50, message = "El modelo debe tener entre 2 y 50 caracteres")
    private String modelo;

    @Schema(description = "Anio del vehiculo", example = "2022")
    @NotNull(message = "El anio es obligatorio")
    @Min(value = 1990, message = "El anio debe ser igual o mayor a 1990")
    private Integer anio;

    @Schema(description = "Color del vehiculo", example = "Blanco")
    @NotBlank(message = "El color es obligatorio")
    @Size(min = 3, max = 30, message = "El color debe tener entre 3 y 30 caracteres")
    private String color;

    @Schema(description = "Precio diario de arriendo", example = "35000")
    @NotNull(message = "El precio diario es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio diario debe ser mayor a 0")
    private BigDecimal precioArriendoDiario;

    @Schema(description = "Kilometraje actual del vehiculo", example = "25000")
    @NotNull(message = "El kilometraje es obligatorio")
    @Min(value = 0, message = "El kilometraje no puede ser negativo")
    private Integer kilometraje;

    @Schema(description = "Indica si el vehiculo esta disponible", example = "true")
    @NotNull(message = "La disponibilidad es obligatoria")
    private Boolean disponible;

    @Schema(description = "Fecha de ingreso del vehiculo", example = "2024-06-01")
    @NotNull(message = "La fecha de ingreso es obligatoria")
    @PastOrPresent(message = "La fecha de ingreso no puede ser futura")
    private LocalDate fechaIngreso;

    @Schema(description = "ID de la categoria asociada", example = "1")
    @NotNull(message = "El id de la categoria es obligatorio")
    private Integer categoriaId;
}
