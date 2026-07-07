package com.duoc.ms_reservas.service;

import com.duoc.ms_reservas.dto.EstadoReservaDTO;
import com.duoc.ms_reservas.dto.EstadoReservaRequestDTO;
import com.duoc.ms_reservas.exception.ResourceNotFoundException;
import com.duoc.ms_reservas.mapper.EstadoReservaMapper;
import com.duoc.ms_reservas.model.EstadoReserva;
import com.duoc.ms_reservas.repository.EstadoReservaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio encargado de la logica de estados de reserva.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EstadoReservaService {

    private final EstadoReservaRepository estadoReservaRepository;
    private final EstadoReservaMapper estadoReservaMapper;

    public List<EstadoReservaDTO> findAll() {
        try {
            log.info("Listando todos los estados de reserva");

            return estadoReservaRepository.findAll()
                    .stream()
                    .map(estadoReservaMapper::toDTO)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.error("Error al listar estados de reserva", e);
            throw e;
        }
    }

    public EstadoReservaDTO findById(Integer id) {
        try {
            log.info("Buscando estado de reserva con id: {}", id);

            EstadoReserva estadoReserva = estadoReservaRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Estado de reserva no encontrado con id: " + id
                    ));

            return estadoReservaMapper.toDTO(estadoReserva);

        } catch (Exception e) {
            log.error("Error al buscar estado de reserva con id: {}", id, e);
            throw e;
        }
    }

    public EstadoReservaDTO save(EstadoReservaRequestDTO requestDTO) {
        try {
            log.info("Creando nuevo estado de reserva");

            EstadoReserva estadoReserva = estadoReservaMapper.toEntity(requestDTO);
            EstadoReserva estadoReservaGuardado = estadoReservaRepository.save(estadoReserva);

            return estadoReservaMapper.toDTO(estadoReservaGuardado);

        } catch (Exception e) {
            log.error("Error al crear estado de reserva", e);
            throw e;
        }
    }

    public EstadoReservaDTO update(Integer id, EstadoReservaRequestDTO requestDTO) {
        try {
            log.info("Actualizando estado de reserva con id: {}", id);

            EstadoReserva estadoReserva = estadoReservaRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Estado de reserva no encontrado con id: " + id
                    ));

            estadoReservaMapper.updateEntity(estadoReserva, requestDTO);

            EstadoReserva estadoReservaActualizado = estadoReservaRepository.save(estadoReserva);

            return estadoReservaMapper.toDTO(estadoReservaActualizado);

        } catch (Exception e) {
            log.error("Error al actualizar estado de reserva con id: {}", id, e);
            throw e;
        }
    }

    public void delete(Integer id) {
        try {
            log.info("Eliminando estado de reserva con id: {}", id);

            EstadoReserva estadoReserva = estadoReservaRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Estado de reserva no encontrado con id: " + id
                    ));

            estadoReservaRepository.delete(estadoReserva);

        } catch (Exception e) {
            log.error("Error al eliminar estado de reserva con id: {}", id, e);
            throw e;
        }
    }
}