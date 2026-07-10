package com.duoc.msempleados.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Centraliza el manejo de errores del microservicio de empleados
 */
@SuppressWarnings("unused")
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> manejarResourceNotFound(ResourceNotFoundException ex,
                                                                 HttpServletRequest request) {
        return crearRespuestaError(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejarValidaciones(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errores.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> manejarRutaNoEncontrada(NoResourceFoundException ex,
                                                                 HttpServletRequest request) {
        return crearRespuestaError(HttpStatus.NOT_FOUND, "Ruta no encontrada", request.getRequestURI());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> manejarMetodoNoSoportado(HttpRequestMethodNotSupportedException ex,
                                                                  HttpServletRequest request) {
        return crearRespuestaError(HttpStatus.METHOD_NOT_ALLOWED, "Operacion HTTP no soportada", request.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> manejarErroresGenerales(Exception ex,
                                                                HttpServletRequest request) {
        return crearRespuestaError(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor", request.getRequestURI());
    }

    private ResponseEntity<ErrorResponse> crearRespuestaError(HttpStatus estado, String mensaje, String ruta) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                estado.value(),
                estado.getReasonPhrase(),
                mensaje,
                ruta
        );

        return ResponseEntity.status(estado).body(error);
    }
}
