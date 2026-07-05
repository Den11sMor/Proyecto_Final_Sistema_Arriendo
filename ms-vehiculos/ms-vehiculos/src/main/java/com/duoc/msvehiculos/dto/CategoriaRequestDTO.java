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
@Schema(description = "Datos necesarios para crear o actualizar una categoria")
public class CategoriaRequestDTO {

    @Schema(description = "Nombre de la categoria", example = "Sedan")
    @NotBlank(message = "El nombre de la categoria es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @Schema(description = "Descripcion de la categoria", example = "Vehiculos compactos para uso diario")
    @NotBlank(message = "La descripcion es obligatoria")
    @Size(min = 5, max = 200, message = "La descripcion debe tener entre 5 y 200 caracteres")
    private String descripcion;

    @Schema(description = "Tarifa base de la categoria", example = "25000")
    @NotNull(message = "La tarifa base es obligatoria")
    @DecimalMin(value = "0.0", inclusive = false, message = "La tarifa base debe ser mayor a 0")
    private BigDecimal tarifaBase;

    @Schema(description = "Capacidad de pasajeros", example = "5")
    @NotNull(message = "La capacidad de pasajeros es obligatoria")
    @Min(value = 1, message = "La capacidad debe ser minimo 1")
    private Integer capacidadPasajeros;

    @Schema(description = "Indica si la categoria esta activa", example = "true")
    @NotNull(message = "El estado activo es obligatorio")
    private Boolean activa;

    @Schema(description = "Fecha de creacion de la categoria", example = "2024-06-01")
    @NotNull(message = "La fecha de creacion es obligatoria")
    @PastOrPresent(message = "La fecha de creacion no puede ser futura")
    private LocalDate fechaCreacion;
}
