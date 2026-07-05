package com.duoc.ms_reservas.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Datos necesarios para crear o actualizar una reserva")
public class ReservaRequestDTO {

    @Schema(description = "ID del cliente", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El id del cliente es obligatorio")
    @Min(value = 1, message = "El id del cliente debe ser mayor o igual a 1")
    private Integer clienteId;

    @NotNull(message = "El id del vehículo es obligatorio")
    @Min(value = 1, message = "El id del vehículo debe ser mayor o igual a 1")
    @Schema(description = "ID del vehículo", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer vehiculoId;

    @NotNull(message = "La fecha de inicio es obligatoria")
    @FutureOrPresent(message = "La fecha de inicio no puede ser pasada")
    @Schema(description = "Fecha de inicio", example = "2026-07-01", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de fin es obligatoria")
    @FutureOrPresent(message = "La fecha de fin no puede ser pasada")
    @Schema(description = "Fecha de término", example = "2026-07-05", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate fechaFin;

    @NotNull(message = "La cantidad de días es obligatoria")
    @Min(value = 1, message = "La cantidad de días debe ser mayor o igual a 1")
    @Schema(description = "Cantidad de días de arriendo", example = "4", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer cantidadDias;

    @NotNull(message = "El monto total es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El monto total debe ser mayor a 0")
    @Schema(description = "Monto total a pagar", example = "120000", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal montoTotal;

    @NotBlank(message = "La observación es obligatoria")
    @Size(min = 3, max = 150, message = "La observación debe tener entre 3 y 150 caracteres")
    @Schema(description = "Observación de la reserva", example = "Reserva para viaje de negocios", requiredMode = Schema.RequiredMode.REQUIRED)
    private String observacion;

    @NotNull(message = "El estado activo de la reserva es obligatorio")
    @Schema(description = "Indica si la reserva queda activa", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean activa;

    @NotNull(message = "El estado de reserva es obligatorio")
    @Min(value = 1, message = "El id del estado de reserva debe ser mayor o igual a 1")
    @Schema(description = "ID del estado de la reserva", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer estadoReservaId;

}
