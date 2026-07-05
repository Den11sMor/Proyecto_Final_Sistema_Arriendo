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

@Slf4j
@Service
@RequiredArgsConstructor
public class EstadoReservaService {

    private final EstadoReservaRepository estadoReservaRepository;
    private final EstadoReservaMapper estadoReservaMapper;

    public List<EstadoReservaDTO> findAll() {
        try {
            // agregado: log para listar todos los estados de reserva
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
            // agregado: log para buscar estado de reserva por id
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
            // agregado: log para crear estado de reserva
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
            // agregado: actualización campo por campo para PUT
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
            // agregado: log para eliminar estado de reserva
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
