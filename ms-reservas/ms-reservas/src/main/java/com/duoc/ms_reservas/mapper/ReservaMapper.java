package com.duoc.ms_reservas.mapper;

import com.duoc.ms_reservas.dto.ReservaDTO;
import com.duoc.ms_reservas.dto.ReservaRequestDTO;
import com.duoc.ms_reservas.model.EstadoReserva;
import com.duoc.ms_reservas.model.Reserva;
import org.springframework.stereotype.Component;

@Component
public class ReservaMapper {

    public ReservaDTO toDTO(Reserva reserva) {
        ReservaDTO dto = new ReservaDTO();

        dto.setId(reserva.getId());
        dto.setClienteId(reserva.getClienteId());
        dto.setVehiculoId(reserva.getVehiculoId());
        dto.setFechaInicio(reserva.getFechaInicio());
        dto.setFechaFin(reserva.getFechaFin());
        dto.setCantidadDias(reserva.getCantidadDias());
        dto.setMontoTotal(reserva.getMontoTotal());
        dto.setObservacion(reserva.getObservacion());
        dto.setActiva(reserva.isActiva());

        if (reserva.getEstadoReserva() != null) {
            dto.setEstadoReservaId(reserva.getEstadoReserva().getId());
            dto.setNombreEstadoReserva(reserva.getEstadoReserva().getNombre());
        }

        return dto;
    }

    public Reserva toEntity(ReservaRequestDTO requestDTO, EstadoReserva estadoReserva) {
        Reserva reserva = new Reserva();

        reserva.setClienteId(requestDTO.getClienteId());
        reserva.setVehiculoId(requestDTO.getVehiculoId());
        reserva.setFechaInicio(requestDTO.getFechaInicio());
        reserva.setFechaFin(requestDTO.getFechaFin());
        reserva.setCantidadDias(requestDTO.getCantidadDias());
        reserva.setMontoTotal(requestDTO.getMontoTotal());
        reserva.setObservacion(requestDTO.getObservacion());
        reserva.setActiva(requestDTO.getActiva());
        reserva.setEstadoReserva(estadoReserva);

        return reserva;
    }

    // agregado: método para actualizar campo por campo en el PUT
    public void updateEntity(Reserva reserva, ReservaRequestDTO requestDTO, EstadoReserva estadoReserva) {
        reserva.setClienteId(requestDTO.getClienteId());
        reserva.setVehiculoId(requestDTO.getVehiculoId());
        reserva.setFechaInicio(requestDTO.getFechaInicio());
        reserva.setFechaFin(requestDTO.getFechaFin());
        reserva.setCantidadDias(requestDTO.getCantidadDias());
        reserva.setMontoTotal(requestDTO.getMontoTotal());
        reserva.setObservacion(requestDTO.getObservacion());
        reserva.setActiva(requestDTO.getActiva());
        reserva.setEstadoReserva(estadoReserva);
    }
}
