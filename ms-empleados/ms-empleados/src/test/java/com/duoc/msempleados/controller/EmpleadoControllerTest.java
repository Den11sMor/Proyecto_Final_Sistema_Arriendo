package com.duoc.msempleados.controller;

import com.duoc.msempleados.dto.EmpleadoDTO;
import com.duoc.msempleados.service.EmpleadoService;
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
class EmpleadoControllerTest {

    @Mock
    private EmpleadoService empleadoService;

    @InjectMocks
    private EmpleadoController empleadoController;

    @Test
    void findById_deberiaRetornarEmpleadoDelService() {
        EmpleadoDTO esperado = new EmpleadoDTO();
        when(empleadoService.findById(1)).thenReturn(esperado);

        ResponseEntity<EmpleadoDTO> respuesta = empleadoController.findById(1);

        assertEquals(200, respuesta.getStatusCode().value());
        assertSame(esperado, respuesta.getBody());
        verify(empleadoService).findById(1);
    }

    @Test
    void listarActivosPorAnio_deberiaDelegarEnService() {
        List<EmpleadoDTO> esperado = List.of(new EmpleadoDTO());
        when(empleadoService.listarActivosPorAnio(2024)).thenReturn(esperado);

        ResponseEntity<List<EmpleadoDTO>> respuesta = empleadoController.listarActivosPorAnio(2024);

        assertEquals(200, respuesta.getStatusCode().value());
        assertSame(esperado, respuesta.getBody());
        verify(empleadoService).listarActivosPorAnio(2024);
    }

    @Test
    void delete_deberiaEliminarEmpleado() {
        ResponseEntity<Void> respuesta = empleadoController.delete(1);

        assertEquals(204, respuesta.getStatusCode().value());
        verify(empleadoService).delete(1);
    }
}
