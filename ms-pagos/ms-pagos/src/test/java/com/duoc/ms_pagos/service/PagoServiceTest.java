package com.duoc.ms_pagos.service;

import com.duoc.ms_pagos.dto.PagoDTO;
import com.duoc.ms_pagos.dto.PagoRequestDTO;
import com.duoc.ms_pagos.dto.ReservaDTO;
import com.duoc.ms_pagos.exception.ResourceNotFoundException;
import com.duoc.ms_pagos.feign.ReservaClient;
import com.duoc.ms_pagos.mapper.PagoMapper;
import com.duoc.ms_pagos.model.Pago;
import com.duoc.ms_pagos.repository.PagoRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias del servicio de pagos.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("PagoService")
class PagoServiceTest {

    @Mock
    private PagoRepository pagoRepository;

    @Mock
    private PagoMapper pagoMapper;

    @Mock
    private ReservaClient reservaClient;

    @InjectMocks
    private PagoService pagoService;

    private Pago pago;
    private PagoDTO pagoDTO;
    private PagoRequestDTO requestDTO;
    private ReservaDTO reservaDTO;

    @BeforeEach
    void setUp() {
        pago = new Pago();
        pago.setId(1);
        pago.setReservaId(10);
        pago.setMetodoPago("Tarjeta de credito");
        pago.setMonto(new BigDecimal("125000"));
        pago.setCodigoTransaccion("TX-2024-0001");
        pago.setPagado(true);
        pago.setFechaPago(LocalDate.of(2024, 4, 20));
        pago.setObservacion("Pago confirmado");

        pagoDTO = new PagoDTO(
                1,
                10,
                "Tarjeta de credito",
                new BigDecimal("125000"),
                "TX-2024-0001",
                true,
                LocalDate.of(2024, 4, 20),
                "Pago confirmado"
        );

        requestDTO = new PagoRequestDTO(
                10,
                "Tarjeta de credito",
                new BigDecimal("125000"),
                "TX-2024-0001",
                true,
                LocalDate.of(2024, 4, 20),
                "Pago confirmado"
        );

        reservaDTO = new ReservaDTO();
        reservaDTO.setId(10);
        reservaDTO.setMontoTotal(new BigDecimal("125000"));
    }

    @Test
    @DisplayName("Debe listar pagos")
    void testFindAll() {
        when(pagoRepository.findAll()).thenReturn(List.of(pago));
        when(pagoMapper.toDTO(pago)).thenReturn(pagoDTO);

        List<PagoDTO> resultado = pagoService.findAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Tarjeta de credito", resultado.get(0).getMetodoPago());
        verify(pagoRepository, times(1)).findAll();
        verify(pagoMapper, times(1)).toDTO(pago);
    }

    @Test
    @DisplayName("Debe buscar pago por id")
    void testFindById() {
        when(pagoRepository.findById(1)).thenReturn(Optional.of(pago));
        when(pagoMapper.toDTO(pago)).thenReturn(pagoDTO);

        PagoDTO resultado = pagoService.findById(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Tarjeta de credito", resultado.getMetodoPago());
        verify(pagoRepository, times(1)).findById(1);
        verify(pagoMapper, times(1)).toDTO(pago);
    }

    @Test
    @DisplayName("Debe lanzar error cuando pago no existe")
    void testFindByIdNoEncontrado() {
        when(pagoRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> pagoService.findById(99));

        verify(pagoRepository, times(1)).findById(99);
        verifyNoInteractions(pagoMapper, reservaClient);
    }

    @Test
    @DisplayName("Debe guardar pago obteniendo monto desde reserva")
    void testSave() {
        when(reservaClient.findById(10)).thenReturn(reservaDTO);
        when(pagoMapper.toEntity(requestDTO)).thenReturn(pago);
        when(pagoRepository.save(pago)).thenReturn(pago);
        when(pagoMapper.toDTO(pago)).thenReturn(pagoDTO);

        PagoDTO resultado = pagoService.save(requestDTO);

        assertNotNull(resultado);
        assertEquals("Tarjeta de credito", resultado.getMetodoPago());
        assertEquals(new BigDecimal("125000"), requestDTO.getMonto());
        verify(reservaClient, times(1)).findById(10);
        verify(pagoMapper, times(1)).toEntity(requestDTO);
        verify(pagoRepository, times(1)).save(pago);
        verify(pagoMapper, times(1)).toDTO(pago);
    }

    @Test
    @DisplayName("Debe actualizar pago")
    void testUpdate() {
        when(pagoRepository.findById(1)).thenReturn(Optional.of(pago));
        when(reservaClient.findById(10)).thenReturn(reservaDTO);
        when(pagoRepository.save(pago)).thenReturn(pago);
        when(pagoMapper.toDTO(pago)).thenReturn(pagoDTO);

        PagoDTO resultado = pagoService.update(1, requestDTO);

        assertNotNull(resultado);
        assertEquals("Tarjeta de credito", resultado.getMetodoPago());
        assertEquals(new BigDecimal("125000"), requestDTO.getMonto());
        verify(pagoRepository, times(1)).findById(1);
        verify(reservaClient, times(1)).findById(10);
        verify(pagoMapper, times(1)).updateEntity(pago, requestDTO);
        verify(pagoRepository, times(1)).save(pago);
    }

    @Test
    @DisplayName("Debe eliminar pago")
    void testDelete() {
        when(pagoRepository.findById(1)).thenReturn(Optional.of(pago));

        pagoService.delete(1);

        verify(pagoRepository, times(1)).findById(1);
        verify(pagoRepository, times(1)).delete(pago);
    }

    @Test
    @DisplayName("Debe buscar pagos por rango de monto")
    void testBuscarPorRangoMonto() {
        BigDecimal min = new BigDecimal("10000");
        BigDecimal max = new BigDecimal("200000");

        when(pagoRepository.buscarPagosPorRangoMonto(min, max)).thenReturn(List.of(pago));
        when(pagoMapper.toDTO(pago)).thenReturn(pagoDTO);

        List<PagoDTO> resultado = pagoService.buscarPorRangoMonto(min, max);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Tarjeta de credito", resultado.get(0).getMetodoPago());
        verify(pagoRepository, times(1)).buscarPagosPorRangoMonto(min, max);
        verify(pagoMapper, times(1)).toDTO(pago);
    }
}