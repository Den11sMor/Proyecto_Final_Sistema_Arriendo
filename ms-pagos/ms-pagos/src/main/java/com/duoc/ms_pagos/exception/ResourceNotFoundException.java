package com.duoc.ms_pagos.exception;

/**
 * Excepcion usada cuando un recurso solicitado no existe.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String mensaje) {
        super(mensaje);
    }
}