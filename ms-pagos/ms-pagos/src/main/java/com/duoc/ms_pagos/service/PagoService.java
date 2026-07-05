package com.duoc.ms_pagos.service;

import com.duoc.ms_pagos.dto.PagoDTO;
import com.duoc.ms_pagos.dto.PagoRequestDTO;
import com.duoc.ms_pagos.dto.ReservaDTO;
import com.duoc.ms_pagos.exception.ResourceNotFoundException;
import com.duoc.ms_pagos.feign.ReservaClient;
import com.duoc.ms_pagos.mapper.PagoMapper;
import com.duoc.ms_pagos.model.Pago;
import com.duoc.ms_pagos.repository.PagoRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PagoService {

    private final PagoRepository pagoRepository;
    private final PagoMapper pagoMapper;
    private final ReservaClient reservaClient;

    public List<PagoDTO> findAll() {
        log.info("Listando todos los pagos");

        try {
            return pagoRepository.findAll()
                    .stream()
                    .map(pagoMapper::toDTO)
                    .toList();
        } catch (Exception e) {
            log.error("Error al listar pagos", e);
            throw e;
        }
    }

    public PagoDTO findById(Integer id) {
        log.info("Buscando pago con id: {}", id);

        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado con id: " + id));

        return pagoMapper.toDTO(pago);
    }

    public PagoDTO save(PagoRequestDTO requestDTO) {
        log.info("Creando pago para reserva id: {}", requestDTO.getReservaId());

        try {
            // agregado: obtiene la reserva desde ms-reservas y usa su monto total
            ReservaDTO reservaDTO = obtenerReserva(requestDTO.getReservaId());
            requestDTO.setMonto(reservaDTO.getMontoTotal());

            Pago pago = pagoMapper.toEntity(requestDTO);
            Pago pagoGuardado = pagoRepository.save(pago);

            return pagoMapper.toDTO(pagoGuardado);
        } catch (FeignException.NotFound e) {
            log.error("Reserva no encontrada con id: {}", requestDTO.getReservaId(), e);
            throw new ResourceNotFoundException("Reserva no encontrada con id: " + requestDTO.getReservaId());
        } catch (Exception e) {
            log.error("Error al crear pago", e);
            throw e;
        }
    }

    public PagoDTO update(Integer id, PagoRequestDTO requestDTO) {
        log.info("Actualizando pago con id: {}", id);

        try {
            Pago pago = pagoRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado con id: " + id));

            // agregado: obtiene la reserva desde ms-reservas y usa su monto total
            ReservaDTO reservaDTO = obtenerReserva(requestDTO.getReservaId());
            requestDTO.setMonto(reservaDTO.getMontoTotal());

            // agregado: actualización campo por campo para PUT
            pagoMapper.updateEntity(pago, requestDTO);

            Pago pagoActualizado = pagoRepository.save(pago);
            return pagoMapper.toDTO(pagoActualizado);
        } catch (FeignException.NotFound e) {
            log.error("Reserva no encontrada con id: {}", requestDTO.getReservaId(), e);
            throw new ResourceNotFoundException("Reserva no encontrada con id: " + requestDTO.getReservaId());
        } catch (Exception e) {
            log.error("Error al actualizar pago con id: {}", id, e);
            throw e;
        }
    }

    public void delete(Integer id) {
        log.info("Eliminando pago con id: {}", id);

        try {
            Pago pago = pagoRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado con id: " + id));

            pagoRepository.delete(pago);
        } catch (Exception e) {
            log.error("Error al eliminar pago con id: {}", id, e);
            throw e;
        }
    }

    public List<PagoDTO> buscarPorRangoMonto(BigDecimal min, BigDecimal max) {
        log.info("Buscando pagos entre montos {} y {}", min, max);

        try {
            return pagoRepository.buscarPagosPorRangoMonto(min, max)
                    .stream()
                    .map(pagoMapper::toDTO)
                    .toList();
        } catch (Exception e) {
            log.error("Error al buscar pagos por rango de monto", e);
            throw e;
        }
    }

    private ReservaDTO obtenerReserva(Integer reservaId) {
        return reservaClient.findById(reservaId);
    }
}
