package com.duoc.ms_pagos.service;

import com.duoc.ms_pagos.dto.PagoDTO;
import com.duoc.ms_pagos.dto.PagoRequestDTO;
import com.duoc.ms_pagos.dto.ReservaDTO;
import com.duoc.ms_pagos.exception.ResourceNotFoundException;
import com.duoc.ms_pagos.feign.ReservaClient;
import com.duoc.ms_pagos.mapper.PagoMapper;
import com.duoc.ms_pagos.model.Pago;
import com.duoc.ms_pagos.repository.PagoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PagoServiceTest {

    @Mock
    private PagoRepository pagoRepository;

    @Mock
    private PagoMapper pagoMapper;

    @Mock
    private ReservaClient reservaClient;

    @InjectMocks
    private PagoService pagoService;

    @Test
    void save_deberiaObtenerReservaYGuardarPago() {
        PagoRequestDTO request = new PagoRequestDTO();
        request.setReservaId(1);

        ReservaDTO reserva = new ReservaDTO();
        reserva.setId(1);
        reserva.setMontoTotal(new BigDecimal("50000"));

        Pago pago = new Pago();
        PagoDTO esperado = new PagoDTO();

        when(reservaClient.findById(1)).thenReturn(reserva);
        when(pagoMapper.toEntity(request)).thenReturn(pago);
        when(pagoRepository.save(pago)).thenReturn(pago);
        when(pagoMapper.toDTO(pago)).thenReturn(esperado);

        PagoDTO resultado = pagoService.save(request);

        assertSame(esperado, resultado);
        assertEquals(new BigDecimal("50000"), request.getMonto());
        verify(reservaClient).findById(1);
        verify(pagoRepository).save(pago);
    }

    @Test
    void findById_deberiaFallarCuandoPagoNoExiste() {
        when(pagoRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> pagoService.findById(99));

        verify(pagoRepository).findById(99);
        verifyNoInteractions(pagoMapper, reservaClient);
    }

    @Test
    void delete_deberiaEliminarPagoExistente() {
        Pago pago = new Pago();
        when(pagoRepository.findById(1)).thenReturn(Optional.of(pago));

        pagoService.delete(1);

        verify(pagoRepository).delete(pago);
    }
}
