package com.duoc.ms_reservas.service;

import com.duoc.ms_reservas.dto.ClienteDTO;
import com.duoc.ms_reservas.dto.ReservaDTO;
import com.duoc.ms_reservas.dto.ReservaRequestDTO;
import com.duoc.ms_reservas.dto.VehiculoDTO;
import com.duoc.ms_reservas.exception.ResourceNotFoundException;
import com.duoc.ms_reservas.feign.ClienteClient;
import com.duoc.ms_reservas.feign.VehiculoClient;
import com.duoc.ms_reservas.mapper.ReservaMapper;
import com.duoc.ms_reservas.model.EstadoReserva;
import com.duoc.ms_reservas.model.Reserva;
import com.duoc.ms_reservas.repository.EstadoReservaRepository;
import com.duoc.ms_reservas.repository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final EstadoReservaRepository estadoReservaRepository;
    private final ReservaMapper reservaMapper;
    private final ClienteClient clienteClient;
    private final VehiculoClient vehiculoClient;

    public List<ReservaDTO> findAll() {
        try {
            log.info("Listando todas las reservas");

            return reservaRepository.findAll()
                    .stream()
                    .map(reservaMapper::toDTO)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.error("Error al listar reservas", e);
            throw e;
        }
    }

    public ReservaDTO findById(Integer id) {
        try {
            log.info("Buscando reserva con id: {}", id);

            Reserva reserva = reservaRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Reserva no encontrada con id: " + id
                    ));

            return reservaMapper.toDTO(reserva);

        } catch (Exception e) {
            log.error("Error al buscar reserva con id: {}", id, e);
            throw e;
        }
    }

    public ReservaDTO save(ReservaRequestDTO requestDTO) {
        try {
            log.info("Creando nueva reserva");

            // REGLA DE NEGOCIO OBLIGATORIA (IE 2.2.1): Validar consistencia cronológica de fechas
            validarFechasReserva(requestDTO.getFechaInicio(), requestDTO.getFechaFin());

            // Verifica que el cliente exista en ms-clientes
            ClienteDTO cliente = clienteClient.obtenerClientePorId(requestDTO.getClienteId());

            if (cliente == null || cliente.getId() == null) {
                throw new ResourceNotFoundException(
                        "Cliente no encontrado con id: " + requestDTO.getClienteId()
                );
            }

            // Verifica que el vehículo exista en ms-vehiculos
            VehiculoDTO vehiculo = vehiculoClient.obtenerVehiculoPorId(requestDTO.getVehiculoId());

            if (vehiculo == null || vehiculo.getId() == null) {
                throw new ResourceNotFoundException(
                        "Vehículo no encontrado con id: " + requestDTO.getVehiculoId()
                );
            }

            // Valida disponibilidad del vehículo
            if (vehiculo.getDisponible() != null && !vehiculo.getDisponible()) {
                throw new IllegalStateException("El vehículo no se encuentra disponible para reservar");
            }

            EstadoReserva estadoReserva = estadoReservaRepository.findById(requestDTO.getEstadoReservaId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Estado de reserva no encontrado con id: " + requestDTO.getEstadoReservaId()
                    ));

            Reserva reserva = reservaMapper.toEntity(requestDTO, estadoReserva);
            Reserva reservaGuardada = reservaRepository.save(reserva);

            return reservaMapper.toDTO(reservaGuardada);

        } catch (Exception e) {
            log.error("Error al crear reserva", e);
            throw e;
        }
    }

    public ReservaDTO update(Integer id, ReservaRequestDTO requestDTO) {
        try {
            log.info("Actualizando reserva con id: {}", id);

            // REGLA DE NEGOCIO OBLIGATORIA (IE 2.2.1): Validar consistencia cronológica de fechas
            validarFechasReserva(requestDTO.getFechaInicio(), requestDTO.getFechaFin());

            Reserva reserva = reservaRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Reserva no encontrada con id: " + id
                    ));

            ClienteDTO cliente = clienteClient.obtenerClientePorId(requestDTO.getClienteId());

            if (cliente == null || cliente.getId() == null) {
                throw new ResourceNotFoundException(
                        "Cliente no encontrado con id: " + requestDTO.getClienteId()
                );
            }

            VehiculoDTO vehiculo = vehiculoClient.obtenerVehiculoPorId(requestDTO.getVehiculoId());

            if (vehiculo == null || vehiculo.getId() == null) {
                throw new ResourceNotFoundException(
                        "Vehículo no encontrado con id: " + requestDTO.getVehiculoId()
                );
            }

            EstadoReserva estadoReserva = estadoReservaRepository.findById(requestDTO.getEstadoReservaId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Estado de reserva no encontrado con id: " + requestDTO.getEstadoReservaId()
                    ));

            reservaMapper.updateEntity(reserva, requestDTO, estadoReserva);

            Reserva reservaActualizada = reservaRepository.save(reserva);

            return reservaMapper.toDTO(reservaActualizada);

        } catch (Exception e) {
            log.error("Error al actualizar reserva con id: {}", id, e);
            throw e;
        }
    }

    public void delete(Integer id) {
        try {
            log.info("Eliminando reserva con id: {}", id);

            Reserva reserva = reservaRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Reserva no encontrada con id: " + id
                    ));

            reservaRepository.delete(reserva);

        } catch (Exception e) {
            log.error("Error al eliminar reserva con id: {}", id, e);
            throw e;
        }
    }

    public List<ReservaDTO> findByFechaInicioDesde(LocalDate fecha) {
        try {
            log.info("Buscando reservas desde la fecha: {}", fecha);

            return reservaRepository.buscarReservasDesdeFecha(fecha)
                    .stream()
                    .map(reservaMapper::toDTO)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.error("Error al buscar reservas desde la fecha: {}", fecha, e);
            throw e;
        }
    }
    // Metodo privado auxiliar que asiste a save y update para mantener la integridad de los datos

    private void validarFechasReserva(LocalDate inicio, LocalDate fin) {
        if (fin.isBefore(inicio)) {
            throw new IllegalArgumentException("La fecha de finalización no puede ser anterior a la fecha de inicio.");
        }
    }
}