package com.duoc.ms_reservas.mapper;

import com.duoc.ms_reservas.dto.EstadoReservaDTO;
import com.duoc.ms_reservas.dto.EstadoReservaRequestDTO;
import com.duoc.ms_reservas.model.EstadoReserva;
import org.springframework.stereotype.Component;

@Component
public class EstadoReservaMapper {

    public EstadoReservaDTO toDTO(EstadoReserva estadoReserva) {
        EstadoReservaDTO dto = new EstadoReservaDTO();

        dto.setId(estadoReserva.getId());
        dto.setNombre(estadoReserva.getNombre());
        dto.setDescripcion(estadoReserva.getDescripcion());
        dto.setPrioridad(estadoReserva.getPrioridad());
        dto.setActivo(estadoReserva.isActivo());
        dto.setFechaCreacion(estadoReserva.getFechaCreacion());

        return dto;
    }

    public EstadoReserva toEntity(EstadoReservaRequestDTO requestDTO) {
        EstadoReserva estadoReserva = new EstadoReserva();

        estadoReserva.setNombre(requestDTO.getNombre());
        estadoReserva.setDescripcion(requestDTO.getDescripcion());
        estadoReserva.setPrioridad(requestDTO.getPrioridad());
        estadoReserva.setActivo(requestDTO.getActivo());
        estadoReserva.setFechaCreacion(requestDTO.getFechaCreacion());

        return estadoReserva;
    }

    // agregado: método para actualizar campo por campo en el PUT
    public void updateEntity(EstadoReserva estadoReserva, EstadoReservaRequestDTO requestDTO) {
        estadoReserva.setNombre(requestDTO.getNombre());
        estadoReserva.setDescripcion(requestDTO.getDescripcion());
        estadoReserva.setPrioridad(requestDTO.getPrioridad());
        estadoReserva.setActivo(requestDTO.getActivo());
        estadoReserva.setFechaCreacion(requestDTO.getFechaCreacion());
    }
}
