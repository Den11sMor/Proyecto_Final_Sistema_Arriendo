package com.duoc.mssucursales.controller;

import com.duoc.mssucursales.dto.SucursalDTO;
import com.duoc.mssucursales.service.SucursalService;
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
class SucursalControllerTest {

    @Mock
    private SucursalService sucursalService;

    @InjectMocks
    private SucursalController sucursalController;

    @Test
    void findById_deberiaRetornarSucursalDelService() {
        SucursalDTO esperado = new SucursalDTO();

        when(sucursalService.findById(1)).thenReturn(esperado);

        ResponseEntity<SucursalDTO> respuesta = sucursalController.findById(1);

        assertEquals(200, respuesta.getStatusCode().value());
        assertSame(esperado, respuesta.getBody());
        verify(sucursalService).findById(1);
    }

    @Test
    void listarOperativasOrdenadas_deberiaDelegarEnService() {
        List<SucursalDTO> esperado = List.of(new SucursalDTO());

        when(sucursalService.listarOperativasOrdenadas()).thenReturn(esperado);

        ResponseEntity<List<SucursalDTO>> respuesta = sucursalController.listarOperativasOrdenadas();

        assertEquals(200, respuesta.getStatusCode().value());
        assertSame(esperado, respuesta.getBody());
        verify(sucursalService).listarOperativasOrdenadas();
    }

    @Test
    void delete_deberiaEliminarSucursal() {
        ResponseEntity<Void> respuesta = sucursalController.delete(1);

        assertEquals(204, respuesta.getStatusCode().value());
        verify(sucursalService).delete(1);
    }
}