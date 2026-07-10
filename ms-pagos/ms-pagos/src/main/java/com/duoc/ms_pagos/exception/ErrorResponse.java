package com.duoc.ms_pagos.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Define la estructura comun para las respuestas de error.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Respuesta estandar para errores del microservicio")
public class ErrorResponse {

    @Schema(description = "Fecha y hora del error", example = "2026-07-07T10:30:00")
    private LocalDateTime timestamp;

    @Schema(description = "Codigo HTTP del error", example = "404")
    private int status;

    @Schema(description = "Tipo de error", example = "Not Found")
    private String error;

    @Schema(description = "Mensaje del error", example = "Pago no encontrado")
    private String message;

    @Schema(description = "Ruta donde ocurrio el error", example = "/api/v1/pagos/99")
    private String path;
}
