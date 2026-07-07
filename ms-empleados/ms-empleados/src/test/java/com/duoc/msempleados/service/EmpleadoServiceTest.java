package com.duoc.msempleados.service;

import com.duoc.msempleados.dto.EmpleadoDTO;
import com.duoc.msempleados.dto.EmpleadoRequestDTO;
import com.duoc.msempleados.exception.ResourceNotFoundException;
import com.duoc.msempleados.mapper.EmpleadoMapper;
import com.duoc.msempleados.model.Empleado;
import com.duoc.msempleados.repository.EmpleadoRepository;
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
 * Pruebas unitarias del servicio de empleados.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("EmpleadoService")
class EmpleadoServiceTest {

    @Mock
    private EmpleadoMapper empleadoMapper;

    @Mock
    private EmpleadoRepository empleadoRepository;

    @InjectMocks
    private EmpleadoService empleadoService;

    private Empleado empleado;
    private EmpleadoDTO empleadoDTO;
    private EmpleadoRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        empleado = new Empleado();
        empleado.setId(1);
        empleado.setRut("12345678-9");
        empleado.setNombre("Juan Perez");
        empleado.setCargo("Ejecutivo");
        empleado.setEmail("juan.perez@empresa.cl");
        empleado.setSueldo(new BigDecimal("850000"));
        empleado.setActivo(true);
        empleado.setFechaIngreso(LocalDate.of(2024, 3, 15));

        empleadoDTO = new EmpleadoDTO();
        empleadoDTO.setId(1);
        empleadoDTO.setRut("12345678-9");
        empleadoDTO.setNombre("Juan Perez");
        empleadoDTO.setCargo("Ejecutivo");
        empleadoDTO.setEmail("juan.perez@empresa.cl");
        empleadoDTO.setSueldo(new BigDecimal("850000"));
        empleadoDTO.setActivo(true);
        empleadoDTO.setFechaIngreso(LocalDate.of(2024, 3, 15));

        requestDTO = new EmpleadoRequestDTO();
        requestDTO.setRut("12345678-9");
        requestDTO.setNombre("Juan Perez");
        requestDTO.setCargo("Ejecutivo");
        requestDTO.setEmail("juan.perez@empresa.cl");
        requestDTO.setSueldo(new BigDecimal("850000"));
        requestDTO.setActivo(true);
        requestDTO.setFechaIngreso(LocalDate.of(2024, 3, 15));
    }

    @Test
    @DisplayName("Debe listar empleados")
    void testFindAll() {
        when(empleadoRepository.findAll()).thenReturn(List.of(empleado));
        when(empleadoMapper.toDTO(empleado)).thenReturn(empleadoDTO);

        List<EmpleadoDTO> resultado = empleadoService.findAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Juan Perez", resultado.get(0).getNombre());
        verify(empleadoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe buscar empleado por id")
    void testFindById() {
        when(empleadoRepository.findById(1)).thenReturn(Optional.of(empleado));
        when(empleadoMapper.toDTO(empleado)).thenReturn(empleadoDTO);

        EmpleadoDTO resultado = empleadoService.findById(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Juan Perez", resultado.getNombre());
        verify(empleadoRepository, times(1)).findById(1);
        verify(empleadoMapper, times(1)).toDTO(empleado);
    }

    @Test
    @DisplayName("Debe lanzar error cuando empleado no existe")
    void testFindByIdNoEncontrado() {
        when(empleadoRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> empleadoService.findById(99));

        verify(empleadoRepository, times(1)).findById(99);
        verifyNoInteractions(empleadoMapper);
    }

    @Test
    @DisplayName("Debe guardar empleado")
    void testSave() {
        when(empleadoMapper.toEntity(requestDTO)).thenReturn(empleado);
        when(empleadoRepository.save(empleado)).thenReturn(empleado);
        when(empleadoMapper.toDTO(empleado)).thenReturn(empleadoDTO);

        EmpleadoDTO resultado = empleadoService.save(requestDTO);

        assertNotNull(resultado);
        assertEquals("Juan Perez", resultado.getNombre());
        verify(empleadoMapper, times(1)).toEntity(requestDTO);
        verify(empleadoRepository, times(1)).save(empleado);
        verify(empleadoMapper, times(1)).toDTO(empleado);
    }

    @Test
    @DisplayName("Debe actualizar empleado")
    void testUpdate() {
        when(empleadoRepository.findById(1)).thenReturn(Optional.of(empleado));
        when(empleadoRepository.save(any(Empleado.class))).thenReturn(empleado);
        when(empleadoMapper.toDTO(empleado)).thenReturn(empleadoDTO);

        EmpleadoDTO resultado = empleadoService.update(1, requestDTO);

        assertNotNull(resultado);
        assertEquals("Juan Perez", resultado.getNombre());
        verify(empleadoRepository, times(1)).findById(1);
        verify(empleadoMapper, times(1)).updateEntity(empleado, requestDTO);
        verify(empleadoRepository, times(1)).save(empleado);
    }

    @Test
    @DisplayName("Debe eliminar empleado")
    void testDelete() {
        when(empleadoRepository.findById(1)).thenReturn(Optional.of(empleado));

        empleadoService.delete(1);

        verify(empleadoRepository, times(1)).findById(1);
        verify(empleadoRepository, times(1)).delete(empleado);
    }

    @Test
    @DisplayName("Debe listar empleados activos por anio")
    void testListarActivosPorAnio() {
        when(empleadoRepository.listarEmpleadosActivosPorAnio(2024)).thenReturn(List.of(empleado));
        when(empleadoMapper.toDTO(empleado)).thenReturn(empleadoDTO);

        List<EmpleadoDTO> resultado = empleadoService.listarActivosPorAnio(2024);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Juan Perez", resultado.get(0).getNombre());
        verify(empleadoRepository, times(1)).listarEmpleadosActivosPorAnio(2024);
    }
}