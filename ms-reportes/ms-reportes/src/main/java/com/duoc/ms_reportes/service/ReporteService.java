package com.duoc.ms_reportes.service;

import com.duoc.ms_reportes.dto.PagoDTO;
import com.duoc.ms_reportes.dto.ReporteDTO;
import com.duoc.ms_reportes.dto.ReporteRequestDTO;
import com.duoc.ms_reportes.dto.ReservaDTO;
import com.duoc.ms_reportes.exception.ResourceNotFoundException;
import com.duoc.ms_reportes.feign.PagoClient;
import com.duoc.ms_reportes.feign.ReservaClient;
import com.duoc.ms_reportes.mapper.ReporteMapper;
import com.duoc.ms_reportes.model.Reporte;
import com.duoc.ms_reportes.repository.ReporteRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReporteService {

    private final ReporteRepository reporteRepository;
    private final ReporteMapper reporteMapper;
    private final ReservaClient reservaClient;
    private final PagoClient pagoClient;

    public List<ReporteDTO> findAll() {
        log.info("Listando todos los reportes");

        return reporteRepository.findAll()
                .stream()
                .map(reporteMapper::toDTO)
                .toList();
    }

    public ReporteDTO findById(Integer id) {
        log.info("Buscando reporte con id: {}", id);

        Reporte reporte = reporteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reporte no encontrado con id: " + id));

        return reporteMapper.toDTO(reporte);
    }

    public ReporteDTO save(ReporteRequestDTO requestDTO) {
        log.info("Creando reporte para reserva {} y pago {}", requestDTO.getReservaId(), requestDTO.getPagoId());

        DatosExternos datosExternos = obtenerDatosExternos(requestDTO);
        validarPagoPerteneceAReserva(requestDTO, datosExternos.pagoDTO());

        Reporte reporte = reporteMapper.toEntity(requestDTO, datosExternos.reservaDTO(), datosExternos.pagoDTO());
        Reporte reporteGuardado = reporteRepository.save(reporte);

        return reporteMapper.toDTO(reporteGuardado);
    }

    public ReporteDTO update(Integer id, ReporteRequestDTO requestDTO) {
        log.info("Actualizando reporte con id: {}", id);

        Reporte reporte = reporteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reporte no encontrado con id: " + id));

        DatosExternos datosExternos = obtenerDatosExternos(requestDTO);
        validarPagoPerteneceAReserva(requestDTO, datosExternos.pagoDTO());

        reporteMapper.updateEntity(reporte, requestDTO, datosExternos.reservaDTO(), datosExternos.pagoDTO());
        Reporte reporteActualizado = reporteRepository.save(reporte);

        return reporteMapper.toDTO(reporteActualizado);
    }

    public void delete(Integer id) {
        log.info("Eliminando reporte con id: {}", id);

        Reporte reporte = reporteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reporte no encontrado con id: " + id));

        reporteRepository.delete(reporte);
    }

    public List<ReporteDTO> findByReservaId(Integer reservaId) {
        log.info("Buscando reportes por reserva id: {}", reservaId);

        return reporteRepository.findByReservaId(reservaId)
                .stream()
                .map(reporteMapper::toDTO)
                .toList();
    }

    public List<ReporteDTO> findByPagoConfirmado(boolean confirmado) {
        log.info("Buscando reportes con pago confirmado: {}", confirmado);

        return reporteRepository.buscarPorPagoConfirmado(confirmado)
                .stream()
                .map(reporteMapper::toDTO)
                .toList();
    }

    private DatosExternos obtenerDatosExternos(ReporteRequestDTO requestDTO) {
        try {
            ReservaDTO reservaDTO = reservaClient.findAll()
                    .stream()
                    .filter(reserva -> requestDTO.getReservaId().equals(reserva.getId()))
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Reserva no encontrada con id: " + requestDTO.getReservaId()
                    ));

            PagoDTO pagoDTO = pagoClient.findAll()
                    .stream()
                    .filter(pago -> requestDTO.getPagoId().equals(pago.getId()))
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Pago no encontrado con id: " + requestDTO.getPagoId()
                    ));

            return new DatosExternos(reservaDTO, pagoDTO);
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Reserva o pago no encontrado para generar reporte");
        }
    }

    private void validarPagoPerteneceAReserva(ReporteRequestDTO requestDTO, PagoDTO pagoDTO) {
        if (!requestDTO.getReservaId().equals(pagoDTO.getReservaId())) {
            throw new IllegalArgumentException("El pago no pertenece a la reserva indicada");
        }
    }

    private record DatosExternos(ReservaDTO reservaDTO, PagoDTO pagoDTO) {
    }
}
