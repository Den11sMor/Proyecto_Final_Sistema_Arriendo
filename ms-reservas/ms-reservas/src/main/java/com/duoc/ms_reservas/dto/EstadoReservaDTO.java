package com.duoc.ms_reservas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true) // Necesario al heredar de RepresentationModel
@Schema(description = "Datos de un estado de reserva devueltos por la API")
public class EstadoReservaDTO extends RepresentationModel<EstadoReservaDTO> {

    @Schema(description = "ID único del estado", example = "1")
    private Integer id;
    @Schema(description = "Nombre del estado", example = "Pendiente")
    private String nombre;
    @Schema(description = "Descripción del estado", example = "En espera de confirmación")
    private String descripcion;
    @Schema(description = "Prioridad del estado", example = "1")
    private Integer prioridad;
    @Schema(description = "Indica si el estado se encuentra activo", example = "true")
    private boolean activo;
    @Schema(description = "Fecha de creación del estado", example = "2026-06-01T10:00:00")
    private LocalDateTime fechaCreacion;

}
