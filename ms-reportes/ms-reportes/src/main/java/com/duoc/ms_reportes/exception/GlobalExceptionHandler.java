package com.duoc.ms_reportes.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * Centraliza el manejo de errores del microservicio de reportes
 */
@SuppressWarnings("unused")
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> manejarResourceNotFound(ResourceNotFoundException ex,
                                                                 HttpServletRequest request) {
        return crearError(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> manejarValidaciones(MethodArgumentNotValidException ex,
                                                             HttpServletRequest request) {
        String mensaje = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return crearError(HttpStatus.BAD_REQUEST, mensaje, request.getRequestURI());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> manejarRutaNoEncontrada(NoResourceFoundException ex,
                                                                 HttpServletRequest request) {
        return crearError(HttpStatus.NOT_FOUND, "Ruta no encontrada", request.getRequestURI());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> manejarOperacionNoSoportada(HttpRequestMethodNotSupportedException ex,
                                                                     HttpServletRequest request) {
        return crearError(HttpStatus.METHOD_NOT_ALLOWED, "Metodo HTTP no soportado", request.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> manejarErrorGeneral(Exception ex,
                                                            HttpServletRequest request) {
        return crearError(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno: " + ex.getMessage(), request.getRequestURI());
    }

    private ResponseEntity<ErrorResponse> crearError(HttpStatus status, String message, String path) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                path
        );

        return ResponseEntity.status(status).body(error);
    }
}
