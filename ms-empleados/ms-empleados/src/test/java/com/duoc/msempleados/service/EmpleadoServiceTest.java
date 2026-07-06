package com.duoc.msempleados.service;

import com.duoc.msempleados.dto.EmpleadoDTO;
import com.duoc.msempleados.dto.EmpleadoRequestDTO;
import com.duoc.msempleados.exception.ResourceNotFoundException;
import com.duoc.msempleados.mapper.EmpleadoMapper;
import com.duoc.msempleados.model.Empleado;
import com.duoc.msempleados.repository.EmpleadoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmpleadoServiceTest {

    @Mock
    private EmpleadoMapper empleadoMapper;

    @Mock
    private EmpleadoRepository empleadoRepository;

    @InjectMocks
    private EmpleadoService empleadoService;

    @Test
    void findById_deberiaRetornarEmpleado() {
        Empleado empleado = new Empleado();
        EmpleadoDTO esperado = new EmpleadoDTO();

        when(empleadoRepository.findById(1)).thenReturn(Optional.of(empleado));
        when(empleadoMapper.toDTO(empleado)).thenReturn(esperado);

        EmpleadoDTO resultado = empleadoService.findById(1);

        assertSame(esperado, resultado);
        verify(empleadoRepository).findById(1);
        verify(empleadoMapper).toDTO(empleado);
    }

    @Test
    void save_deberiaGuardarYRetornarEmpleado() {
        EmpleadoRequestDTO request = new EmpleadoRequestDTO();
        Empleado empleado = new Empleado();
        EmpleadoDTO esperado = new EmpleadoDTO();

        when(empleadoMapper.toEntity(request)).thenReturn(empleado);
        when(empleadoRepository.save(empleado)).thenReturn(empleado);
        when(empleadoMapper.toDTO(empleado)).thenReturn(esperado);

        EmpleadoDTO resultado = empleadoService.save(request);

        assertSame(esperado, resultado);
        verify(empleadoRepository).save(empleado);
    }

    @Test
    void findById_deberiaFallarCuandoEmpleadoNoExiste() {
        when(empleadoRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> empleadoService.findById(99));

        verify(empleadoRepository).findById(99);
        verifyNoInteractions(empleadoMapper);
    }
}
