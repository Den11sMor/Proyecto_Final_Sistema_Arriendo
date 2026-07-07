package com.duoc.msempleados.exception;

public class ResourceNotFoundException extends  RuntimeException{
    /**
     * Excepcion usada cuando un recurso solicitado no existe
     */
    public ResourceNotFoundException(String mensaje){
        super(mensaje);
    }

}
