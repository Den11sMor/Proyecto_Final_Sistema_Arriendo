package com.duoc.ms_clientes.service;

import com.duoc.ms_clientes.dto.DireccionDTO;
import com.duoc.ms_clientes.dto.DireccionRequestDTO;
import com.duoc.ms_clientes.exception.ResourceNotFoundException;
import com.duoc.ms_clientes.mapper.DireccionMapper;
import com.duoc.ms_clientes.model.Cliente;
import com.duoc.ms_clientes.model.Direccion;
import com.duoc.ms_clientes.repository.ClienteRepository;
import com.duoc.ms_clientes.repository.DireccionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DireccionServiceTest {

    @Mock
    private DireccionRepository direccionRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private DireccionMapper direccionMapper;

    @InjectMocks
    private DireccionService direccionService;

    private Cliente cliente;
    private Direccion direccion;
    private DireccionDTO direccionDTO;
    private DireccionRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(1);

        direccion = new Direccion();
        direccion.setId(1);
        direccion.setCalle("Av Siempre Viva");
        direccion.setNumero(742);
        direccion.setComuna("Santiago");
        direccion.setCiudad("Santiago");
        direccion.setReferencia("Casa azul");
        direccion.setPrincipal(true);
        direccion.setFechaRegistro(LocalDate.now());
        direccion.setCliente(cliente);

        direccionDTO = new DireccionDTO(1, "Av Siempre Viva", 742, "Santiago",
                "Santiago", "Casa azul", true, LocalDate.now(), 1);

        requestDTO = new DireccionRequestDTO("Av Siempre Viva", 742, "Santiago",
                "Santiago", "Casa azul", true, LocalDate.now(), 1);
    }

    @Test
    void testFindAll() {
        when(direccionRepository.findAll()).thenReturn(List.of(direccion));
        when(direccionMapper.toDTO(direccion)).thenReturn(direccionDTO);

        List<DireccionDTO> resultado = direccionService.findAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Av Siempre Viva", resultado.get(0).getCalle());
        verify(direccionRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(direccionRepository.findById(1)).thenReturn(Optional.of(direccion));
        when(direccionMapper.toDTO(direccion)).thenReturn(direccionDTO);

        DireccionDTO resultado = direccionService.findById(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        verify(direccionRepository, times(1)).findById(1);
    }

    @Test
    void testFindByIdNoEncontrado() {
        when(direccionRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> direccionService.findById(99));
        verify(direccionRepository, times(1)).findById(99);
    }

    @Test
    void testSave() {
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        when(direccionMapper.toEntity(requestDTO, cliente)).thenReturn(direccion);
        when(direccionRepository.save(direccion)).thenReturn(direccion);
        when(direccionMapper.toDTO(direccion)).thenReturn(direccionDTO);

        DireccionDTO resultado = direccionService.save(requestDTO);

        assertNotNull(resultado);
        assertEquals("Av Siempre Viva", resultado.getCalle());
        verify(clienteRepository, times(1)).findById(1);
        verify(direccionRepository, times(1)).save(direccion);
    }

    @Test
    void testUpdate() {
        when(direccionRepository.findById(1)).thenReturn(Optional.of(direccion));
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        when(direccionRepository.save(any(Direccion.class))).thenReturn(direccion);
        when(direccionMapper.toDTO(direccion)).thenReturn(direccionDTO);

        DireccionDTO resultado = direccionService.update(1, requestDTO);

        assertNotNull(resultado);
        assertEquals("Av Siempre Viva", resultado.getCalle());
        verify(direccionRepository, times(1)).findById(1);
        verify(clienteRepository, times(1)).findById(1);
        verify(direccionRepository, times(1)).save(direccion);
    }

    @Test
    void testDelete() {
        when(direccionRepository.findById(1)).thenReturn(Optional.of(direccion));

        direccionService.delete(1);

        verify(direccionRepository, times(1)).findById(1);
        verify(direccionRepository, times(1)).delete(direccion);
    }
}
