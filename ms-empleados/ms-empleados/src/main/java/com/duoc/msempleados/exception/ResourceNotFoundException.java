package com.duoc.msempleados.exception;

public class ResourceNotFoundException extends  RuntimeException{

    public ResourceNotFoundException(String mensaje){
        super(mensaje);
    }

}
