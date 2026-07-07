package com.duoc.ms_reservas.exception;

/**
 * Excepcion usada cuando no existe un recurso solicitado.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String mensaje) {
        super(mensaje);
    }
}
