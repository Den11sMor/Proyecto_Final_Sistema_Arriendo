package com.duoc.ms_reservas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos necesarios para crear o actualizar un estado de reserva")
public class EstadoReservaRequestDTO {

    @NotBlank(message = "El nombre del estado es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    @Schema(description = "Nombre del estado", example = "Pendiente", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;

    @NotBlank(message = "La descripcion es obligatoria")
    @Size(min = 5, max = 150, message = "La descripcion debe tener entre 5 y 150 caracteres")
    @Schema(description = "Descripcion del estado", example = "En espera de confirmacion", requiredMode = Schema.RequiredMode.REQUIRED)
    private String descripcion;

    @NotNull(message = "La prioridad es obligatoria")
    @Min(value = 1, message = "La prioridad debe ser mayor o igual a 1")
    @Schema(description = "Prioridad del estado", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer prioridad;

    @NotNull(message = "El estado activo es obligatorio")
    @Schema(description = "Indica si el estado esta activo", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean activo;

    @NotNull(message = "La fecha de creacion es obligatoria")
    @PastOrPresent(message = "La fecha de creacion no puede ser futura")
    @Schema(description = "Fecha de creacion", example = "2026-06-01T10:00:00", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime fechaCreacion;
}