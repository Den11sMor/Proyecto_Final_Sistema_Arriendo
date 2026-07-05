package com.duoc.ms_clientes.service;

import com.duoc.ms_clientes.dto.ClienteDTO;
import com.duoc.ms_clientes.dto.ClienteRequestDTO;
import com.duoc.ms_clientes.exception.ResourceNotFoundException;
import com.duoc.ms_clientes.mapper.ClienteMapper;
import com.duoc.ms_clientes.model.Cliente;
import com.duoc.ms_clientes.repository.ClienteRepository;
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
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteMapper clienteMapper;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;
    private ClienteDTO clienteDTO;
    private ClienteRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(1);
        cliente.setRut("12345678-9");
        cliente.setNombre("Juan");
        cliente.setApellido("Perez");
        cliente.setEmail("juan.perez@gmail.com");
        cliente.setTelefono(987654321);
        cliente.setActivo(true);
        cliente.setFechaRegistro(LocalDate.now());

        clienteDTO = new ClienteDTO(1, "12345678-9", "Juan", "Perez",
                "juan.perez@gmail.com", 987654321, true, LocalDate.now());

        requestDTO = new ClienteRequestDTO("12345678-9", "Juan", "Perez",
                "juan.perez@gmail.com", 987654321, true, LocalDate.now());
    }

    @Test
    void testFindAll() {
        when(clienteRepository.findAll()).thenReturn(List.of(cliente));
        when(clienteMapper.toDTO(cliente)).thenReturn(clienteDTO);

        List<ClienteDTO> resultado = clienteService.findAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Juan", resultado.get(0).getNombre());
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        when(clienteMapper.toDTO(cliente)).thenReturn(clienteDTO);

        ClienteDTO resultado = clienteService.findById(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        verify(clienteRepository, times(1)).findById(1);
    }

    @Test
    void testFindByIdNoEncontrado() {
        when(clienteRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clienteService.findById(99));
        verify(clienteRepository, times(1)).findById(99);
    }

    @Test
    void testSave() {
        when(clienteMapper.toEntity(requestDTO)).thenReturn(cliente);
        when(clienteRepository.save(cliente)).thenReturn(cliente);
        when(clienteMapper.toDTO(cliente)).thenReturn(clienteDTO);

        ClienteDTO resultado = clienteService.save(requestDTO);

        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    void testUpdate() {
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
        when(clienteMapper.toDTO(cliente)).thenReturn(clienteDTO);

        ClienteDTO resultado = clienteService.update(1, requestDTO);

        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
        verify(clienteRepository, times(1)).findById(1);
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    void testDelete() {
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));

        clienteService.delete(1);

        verify(clienteRepository, times(1)).findById(1);
        verify(clienteRepository, times(1)).delete(cliente);
    }

    @Test
    void testBuscarPorEmail() {
        when(clienteRepository.findByEmailContainingIgnoreCase("gmail")).thenReturn(List.of(cliente));
        when(clienteMapper.toDTO(cliente)).thenReturn(clienteDTO);

        List<ClienteDTO> resultado = clienteService.buscarPorEmail("gmail");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(clienteRepository, times(1)).findByEmailContainingIgnoreCase("gmail");
    }
}
