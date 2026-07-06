package com.duoc.ms_pagos.controller;

import com.duoc.ms_pagos.dto.PagoDTO;
import com.duoc.ms_pagos.service.PagoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PagoControllerTest {

    @Mock
    private PagoService pagoService;

    @InjectMocks
    private PagoController pagoController;

    @Test
    void findById_deberiaRetornarPagoDelService() {
        PagoDTO esperado = new PagoDTO();
        when(pagoService.findById(1)).thenReturn(esperado);

        ResponseEntity<PagoDTO> respuesta = pagoController.findById(1);

        assertEquals(200, respuesta.getStatusCode().value());
        assertSame(esperado, respuesta.getBody());
        verify(pagoService).findById(1);
    }

    @Test
    void buscarPorRangoMonto_deberiaDelegarEnService() {
        BigDecimal min = new BigDecimal("10000");
        BigDecimal max = new BigDecimal("50000");
        List<PagoDTO> esperado = List.of(new PagoDTO());

        when(pagoService.buscarPorRangoMonto(min, max)).thenReturn(esperado);

        ResponseEntity<List<PagoDTO>> respuesta = pagoController.buscarPorRangoMonto(min, max);

        assertEquals(200, respuesta.getStatusCode().value());
        assertSame(esperado, respuesta.getBody());
        verify(pagoService).buscarPorRangoMonto(min, max);
    }

    @Test
    void delete_deberiaEliminarPago() {
        ResponseEntity<Void> respuesta = pagoController.delete(1);

        assertEquals(204, respuesta.getStatusCode().value());
        verify(pagoService).delete(1);
    }
}
