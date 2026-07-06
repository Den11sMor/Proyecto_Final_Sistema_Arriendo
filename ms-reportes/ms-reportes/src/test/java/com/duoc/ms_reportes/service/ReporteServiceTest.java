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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReporteServiceTest {

    @Mock
    private ReporteRepository reporteRepository;

    @Mock
    private ReporteMapper reporteMapper;

    @Mock
    private ReservaClient reservaClient;

    @Mock
    private PagoClient pagoClient;

    @InjectMocks
    private ReporteService reporteService;

    @Test
    void save_deberiaValidarDatosExternosYGuardarReporte() {
        ReporteRequestDTO request = crearRequest();

        ReservaDTO reserva = new ReservaDTO();
        reserva.setId(1);

        PagoDTO pago = new PagoDTO();
        pago.setId(2);
        pago.setReservaId(1);

        Reporte reporte = new Reporte();
        ReporteDTO esperado = new ReporteDTO();

        when(reservaClient.findAll()).thenReturn(List.of(reserva));
        when(pagoClient.findAll()).thenReturn(List.of(pago));
        when(reporteMapper.toEntity(request, reserva, pago)).thenReturn(reporte);
        when(reporteRepository.save(reporte)).thenReturn(reporte);
        when(reporteMapper.toDTO(reporte)).thenReturn(esperado);

        ReporteDTO resultado = reporteService.save(request);

        assertSame(esperado, resultado);
        verify(reservaClient).findAll();
        verify(pagoClient).findAll();
        verify(reporteRepository).save(reporte);
    }

    @Test
    void save_deberiaFallarCuandoPagoNoPerteneceAReserva() {
        ReporteRequestDTO request = crearRequest();

        ReservaDTO reserva = new ReservaDTO();
        reserva.setId(1);

        PagoDTO pago = new PagoDTO();
        pago.setId(2);
        pago.setReservaId(99);

        when(reservaClient.findAll()).thenReturn(List.of(reserva));
        when(pagoClient.findAll()).thenReturn(List.of(pago));

        assertThrows(IllegalArgumentException.class, () -> reporteService.save(request));

        verifyNoInteractions(reporteRepository, reporteMapper);
    }

    @Test
    void findById_deberiaFallarCuandoReporteNoExiste() {
        when(reporteRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> reporteService.findById(99));
    }

    private ReporteRequestDTO crearRequest() {
        ReporteRequestDTO request = new ReporteRequestDTO();
        request.setReservaId(1);
        request.setPagoId(2);
        return request;
    }
}