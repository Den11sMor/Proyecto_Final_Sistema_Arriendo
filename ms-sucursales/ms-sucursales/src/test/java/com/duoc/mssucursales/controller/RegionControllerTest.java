package com.duoc.mssucursales.controller;

import com.duoc.mssucursales.dto.RegionDTO;
import com.duoc.mssucursales.service.RegionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegionControllerTest {

    @Mock
    private RegionService regionService;

    @InjectMocks
    private RegionController regionController;

    @Test
    void findById_deberiaRetornarRegionDelService() {
        RegionDTO esperado = new RegionDTO();

        when(regionService.findById(1)).thenReturn(esperado);

        ResponseEntity<RegionDTO> respuesta = regionController.findById(1);

        assertEquals(200, respuesta.getStatusCode().value());
        assertSame(esperado, respuesta.getBody());
        verify(regionService).findById(1);
    }

    @Test
    void delete_deberiaEliminarRegion() {
        ResponseEntity<Void> respuesta = regionController.delete(1);

        assertEquals(204, respuesta.getStatusCode().value());
        verify(regionService).delete(1);
    }
}
