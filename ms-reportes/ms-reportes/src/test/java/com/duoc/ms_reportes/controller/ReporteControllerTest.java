package com.duoc.ms_reportes.controller;

import com.duoc.ms_reportes.dto.ReporteDTO;
import com.duoc.ms_reportes.service.ReporteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReporteControllerTest {

    @Mock
    private ReporteService reporteService;

    @InjectMocks
    private ReporteController reporteController;

    @Test
    void findById_deberiaRetornarReporteDelService() {
        ReporteDTO esperado = new ReporteDTO();
        when(reporteService.findById(1)).thenReturn(esperado);

        ResponseEntity<ReporteDTO> respuesta = reporteController.findById(1);

        assertEquals(200, respuesta.getStatusCode().value());
        assertSame(esperado, respuesta.getBody());
        verify(reporteService).findById(1);
    }

    @Test
    void findByReservaId_deberiaDelegarEnService() {
        List<ReporteDTO> esperado = List.of(new ReporteDTO());
        when(reporteService.findByReservaId(1)).thenReturn(esperado);

        ResponseEntity<List<ReporteDTO>> respuesta = reporteController.findByReservaId(1);

        assertEquals(200, respuesta.getStatusCode().value());
        assertSame(esperado, respuesta.getBody());
        verify(reporteService).findByReservaId(1);
    }

    @Test
    void delete_deberiaEliminarReporte() {
        ResponseEntity<Void> respuesta = reporteController.delete(1);

        assertEquals(204, respuesta.getStatusCode().value());
        verify(reporteService).delete(1);
    }
}