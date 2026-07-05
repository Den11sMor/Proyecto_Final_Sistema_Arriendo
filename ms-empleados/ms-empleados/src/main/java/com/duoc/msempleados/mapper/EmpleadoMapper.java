package com.duoc.msempleados.mapper;

import com.duoc.msempleados.dto.EmpleadoDTO;
import com.duoc.msempleados.dto.EmpleadoRequestDTO;
import com.duoc.msempleados.model.Empleado;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
public class EmpleadoMapper {

    public EmpleadoDTO toDTO(Empleado empleado){
        if (empleado == null){
            return null;
        }

        EmpleadoDTO dto = new EmpleadoDTO();

        dto.setId(empleado.getId());
        dto.setRut(empleado.getRut());
        dto.setNombre(empleado.getNombre());
        dto.setCargo(empleado.getCargo());
        dto.setEmail(empleado.getEmail());
        dto.setSueldo(empleado.getSueldo());
        dto.setActivo(empleado.getActivo());
        dto.setFechaIngreso(empleado.getFechaIngreso());

        return dto;
    }

    public  Empleado toEntity(EmpleadoRequestDTO request){
        if(request == null){
            return null;
        }

        Empleado empleado = new Empleado();
        empleado.setRut(request.getRut());
        empleado.setNombre(request.getNombre());
        empleado.setCargo(request.getCargo());
        empleado.setEmail(request.getEmail());
        empleado.setSueldo(request.getSueldo());
        empleado.setActivo(request.getActivo());
        empleado.setFechaIngreso(request.getFechaIngreso());

        return empleado;
    }

    public void updateEntity(Empleado empleado, EmpleadoRequestDTO request){
        empleado.setRut(request.getRut());
        empleado.setNombre(request.getNombre());
        empleado.setCargo(request.getCargo());
        empleado.setEmail(request.getEmail());
        empleado.setSueldo(request.getSueldo());
        empleado.setActivo(request.getActivo());
        empleado.setFechaIngreso(request.getFechaIngreso());
    }

}
