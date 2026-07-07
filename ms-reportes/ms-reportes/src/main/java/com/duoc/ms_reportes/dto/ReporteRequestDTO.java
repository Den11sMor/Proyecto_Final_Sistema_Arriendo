package com.duoc.ms_reportes.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos requeridos para crear o actualizar un reporte")
public class ReporteRequestDTO {

    @NotNull(message = "El id de la reserva es obligatorio")
    @Positive(message = "El id de la reserva debe ser positivo")
    @Schema(description = "ID de la reserva asociada", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer reservaId;

    @NotNull(message = "El id del pago es obligatorio")
    @Positive(message = "El id del pago debe ser positivo")
    @Schema(description = "ID del pago asociado", example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer pagoId;

    @NotBlank(message = "El tipo de reporte es obligatorio")
    @Size(min = 3, max = 50, message = "El tipo de reporte debe tener entre 3 y 50 caracteres")
    @Schema(description = "Tipo de reporte", example = "RESUMEN_RESERVA", requiredMode = Schema.RequiredMode.REQUIRED)
    private String tipoReporte;

    @NotBlank(message = "La descripcion es obligatoria")
    @Size(min = 3, max = 200, message = "La descripcion debe tener entre 3 y 200 caracteres")
    @Schema(description = "Descripcion del reporte", example = "Reporte generado para reserva confirmada", requiredMode = Schema.RequiredMode.REQUIRED)
    private String descripcion;
}