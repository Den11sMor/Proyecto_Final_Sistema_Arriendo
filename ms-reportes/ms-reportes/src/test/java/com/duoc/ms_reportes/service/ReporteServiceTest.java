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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    private Reporte reporte;
    private ReporteDTO reporteDTO;
    private ReporteRequestDTO request;
    private ReservaDTO reservaDTO;
    private PagoDTO pagoDTO;

    @BeforeEach
    void setUp() {
        request = new ReporteRequestDTO(
                10,
                5,
                "RESUMEN_RESERVA",
                "Reporte generado para reserva confirmada"
        );

        reservaDTO = new ReservaDTO(
                10,
                3,
                7,
                LocalDate.of(2024, 4, 15),
                LocalDate.of(2024, 4, 20),
                5,
                new BigDecimal("125000"),
                "Reserva confirmada",
                true,
                1,
                "Confirmada"
        );

        pagoDTO = new PagoDTO(
                5,
                10,
                "Tarjeta de credito",
                new BigDecimal("125000"),
                "TX-2024-0001",
                true,
                LocalDate.of(2024, 4, 20),
                "Pago confirmado"
        );

        reporte = new Reporte(
                1,
                10,
                5,
                "RESUMEN_RESERVA",
                LocalDate.of(2024, 4, 20),
                "Reporte generado para reserva confirmada",
                new BigDecimal("125000"),
                new BigDecimal("125000"),
                true,
                true
        );

        reporteDTO = new ReporteDTO(
                1,
                10,
                5,
                "RESUMEN_RESERVA",
                LocalDate.of(2024, 4, 20),
                "Reporte generado para reserva confirmada",
                new BigDecimal("125000"),
                new BigDecimal("125000"),
                true,
                true
        );
    }

    @Test
    @DisplayName("Debe listar todos los reportes")
    void testFindAll() {
        when(reporteRepository.findAll()).thenReturn(List.of(reporte));
        when(reporteMapper.toDTO(reporte)).thenReturn(reporteDTO);

        List<ReporteDTO> resultado = reporteService.findAll();

        assertSame(reporteDTO, resultado.get(0));
        verify(reporteRepository).findAll();
    }

    @Test
    @DisplayName("Debe buscar reporte por id")
    void testFindById() {
        when(reporteRepository.findById(1)).thenReturn(Optional.of(reporte));
        when(reporteMapper.toDTO(reporte)).thenReturn(reporteDTO);

        ReporteDTO resultado = reporteService.findById(1);

        assertSame(reporteDTO, resultado);
        verify(reporteRepository).findById(1);
    }

    @Test
    @DisplayName("Debe fallar cuando el reporte no existe")
    void testFindByIdNoEncontrado() {
        when(reporteRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> reporteService.findById(99));
    }

    @Test
    @DisplayName("Debe guardar reporte validando reserva y pago")
    void testSave() {
        when(reservaClient.findAll()).thenReturn(List.of(reservaDTO));
        when(pagoClient.findAll()).thenReturn(List.of(pagoDTO));
        when(reporteMapper.toEntity(request, reservaDTO, pagoDTO)).thenReturn(reporte);
        when(reporteRepository.save(reporte)).thenReturn(reporte);
        when(reporteMapper.toDTO(reporte)).thenReturn(reporteDTO);

        ReporteDTO resultado = reporteService.save(request);

        assertSame(reporteDTO, resultado);
        verify(reservaClient).findAll();
        verify(pagoClient).findAll();
        verify(reporteRepository).save(reporte);
    }

    @Test
    @DisplayName("Debe actualizar reporte")
    void testUpdate() {
        when(reporteRepository.findById(1)).thenReturn(Optional.of(reporte));
        when(reservaClient.findAll()).thenReturn(List.of(reservaDTO));
        when(pagoClient.findAll()).thenReturn(List.of(pagoDTO));
        when(reporteRepository.save(reporte)).thenReturn(reporte);
        when(reporteMapper.toDTO(reporte)).thenReturn(reporteDTO);

        ReporteDTO resultado = reporteService.update(1, request);

        assertSame(reporteDTO, resultado);
        verify(reporteMapper).updateEntity(reporte, request, reservaDTO, pagoDTO);
        verify(reporteRepository).save(reporte);
    }

    @Test
    @DisplayName("Debe eliminar reporte")
    void testDelete() {
        when(reporteRepository.findById(1)).thenReturn(Optional.of(reporte));

        reporteService.delete(1);

        verify(reporteRepository).delete(reporte);
    }

    @Test
    @DisplayName("Debe buscar reportes por reserva")
    void testFindByReservaId() {
        when(reporteRepository.findByReservaId(10)).thenReturn(List.of(reporte));
        when(reporteMapper.toDTO(reporte)).thenReturn(reporteDTO);

        List<ReporteDTO> resultado = reporteService.findByReservaId(10);

        assertSame(reporteDTO, resultado.get(0));
        verify(reporteRepository).findByReservaId(10);
    }

    @Test
    @DisplayName("Debe buscar reportes por pago confirmado")
    void testFindByPagoConfirmado() {
        when(reporteRepository.buscarPorPagoConfirmado(true)).thenReturn(List.of(reporte));
        when(reporteMapper.toDTO(reporte)).thenReturn(reporteDTO);

        List<ReporteDTO> resultado = reporteService.findByPagoConfirmado(true);

        assertSame(reporteDTO, resultado.get(0));
        verify(reporteRepository).buscarPorPagoConfirmado(true);
    }

    @Test
    @DisplayName("Debe fallar cuando el pago no pertenece a la reserva")
    void testSavePagoNoPerteneceAReserva() {
        PagoDTO pagoOtraReserva = new PagoDTO(
                5,
                99,
                "Tarjeta de credito",
                new BigDecimal("125000"),
                "TX-2024-0001",
                true,
                LocalDate.of(2024, 4, 20),
                "Pago confirmado"
        );

        when(reservaClient.findAll()).thenReturn(List.of(reservaDTO));
        when(pagoClient.findAll()).thenReturn(List.of(pagoOtraReserva));

        assertThrows(IllegalArgumentException.class, () -> reporteService.save(request));
        verifyNoInteractions(reporteRepository, reporteMapper);
    }

    @Test
    @DisplayName("Debe fallar cuando la reserva no existe")
    void testSaveReservaNoEncontrada() {
        when(reservaClient.findAll()).thenReturn(List.of());

        assertThrows(ResourceNotFoundException.class, () -> reporteService.save(request));
        verifyNoInteractions(reporteRepository, reporteMapper);
    }
}