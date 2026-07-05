package com.duoc.ms_reservas.service;

import com.duoc.ms_reservas.dto.EstadoReservaDTO;
import com.duoc.ms_reservas.dto.EstadoReservaRequestDTO;
import com.duoc.ms_reservas.exception.ResourceNotFoundException;
import com.duoc.ms_reservas.mapper.EstadoReservaMapper;
import com.duoc.ms_reservas.model.EstadoReserva;
import com.duoc.ms_reservas.repository.EstadoReservaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EstadoReservaServiceTest {

    @Mock
    private EstadoReservaRepository estadoReservaRepository;
    @Mock
    private EstadoReservaMapper estadoReservaMapper;
    @InjectMocks
    private EstadoReservaService estadoReservaService;

    private EstadoReserva estadoReserva;
    private EstadoReservaDTO estadoReservaDTO;
    private EstadoReservaRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        estadoReserva = new EstadoReserva();
        estadoReserva.setId(1);
        estadoReserva.setNombre("Pendiente");

        estadoReservaDTO = new EstadoReservaDTO(1, "Pendiente", "En espera de confirmación", 1, true, LocalDateTime.now());
        requestDTO = new EstadoReservaRequestDTO("Pendiente", "En espera de confirmación", 1, true, LocalDateTime.now());
    }

    @Test
    void findAll_ReturnsEstadosMapeados() {
        when(estadoReservaRepository.findAll()).thenReturn(List.of(estadoReserva));
        when(estadoReservaMapper.toDTO(estadoReserva)).thenReturn(estadoReservaDTO);

        List<EstadoReservaDTO> resultado = estadoReservaService.findAll();

        Assertions.assertEquals(1, resultado.size());
        Assertions.assertEquals("Pendiente", resultado.get(0).getNombre());
    }

    @Test
    void findById_WhenExists_ReturnsEstado() {
        when(estadoReservaRepository.findById(1)).thenReturn(Optional.of(estadoReserva));
        when(estadoReservaMapper.toDTO(estadoReserva)).thenReturn(estadoReservaDTO);

        EstadoReservaDTO resultado = estadoReservaService.findById(1);

        Assertions.assertEquals(1, resultado.getId());
    }

    @Test
    void findById_WhenNotExists_ThrowsResourceNotFoundException() {
        when(estadoReservaRepository.findById(99)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> estadoReservaService.findById(99));
    }

    @Test
    void save_PersistsAndReturnsDTO() {
        when(estadoReservaMapper.toEntity(requestDTO)).thenReturn(estadoReserva);
        when(estadoReservaRepository.save(estadoReserva)).thenReturn(estadoReserva);
        when(estadoReservaMapper.toDTO(estadoReserva)).thenReturn(estadoReservaDTO);

        EstadoReservaDTO resultado = estadoReservaService.save(requestDTO);

        Assertions.assertEquals("Pendiente", resultado.getNombre());
        verify(estadoReservaRepository).save(estadoReserva);
    }

    @Test
    void update_WhenExists_UpdatesAndReturnsDTO() {
        when(estadoReservaRepository.findById(1)).thenReturn(Optional.of(estadoReserva));
        when(estadoReservaRepository.save(estadoReserva)).thenReturn(estadoReserva);
        when(estadoReservaMapper.toDTO(estadoReserva)).thenReturn(estadoReservaDTO);

        EstadoReservaDTO resultado = estadoReservaService.update(1, requestDTO);

        Assertions.assertEquals(1, resultado.getId());
        verify(estadoReservaMapper).updateEntity(estadoReserva, requestDTO);
    }

    @Test
    void delete_WhenExists_DeletesEstado() {
        when(estadoReservaRepository.findById(1)).thenReturn(Optional.of(estadoReserva));

        estadoReservaService.delete(1);

        verify(estadoReservaRepository).delete(estadoReserva);
    }
}
