package com.duoc.ms_reservas.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;

/**
 * Centraliza el manejo de errores del microservicio de reservas.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> manejarResourceNotFound(ResourceNotFoundException ex) {
        Map<String, String> errores = new HashMap<>();
        errores.put("mensaje", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errores);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejarValidaciones(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errores.put(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> manejarConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> errores = new HashMap<>();
        errores.put("mensaje", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Map<String, String>> manejarRutaNoEncontrada(NoResourceFoundException ex) {
        Map<String, String> errores = new HashMap<>();
        errores.put("mensaje", "Ruta no encontrada");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errores);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String, String>> manejarMetodoNoSoportado(HttpRequestMethodNotSupportedException ex) {
        Map<String, String> errores = new HashMap<>();
        errores.put("mensaje", "Metodo HTTP no soportado");
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errores);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> manejarErroresGenerales(Exception ex) {
        Map<String, String> errores = new HashMap<>();
        errores.put("mensaje", "Error interno del servidor");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errores);
    }
}