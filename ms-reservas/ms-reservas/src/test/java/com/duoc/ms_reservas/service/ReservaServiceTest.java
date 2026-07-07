package com.duoc.ms_reservas.service;

import com.duoc.ms_reservas.dto.ClienteDTO;
import com.duoc.ms_reservas.dto.ReservaDTO;
import com.duoc.ms_reservas.dto.ReservaRequestDTO;
import com.duoc.ms_reservas.dto.VehiculoDTO;
import com.duoc.ms_reservas.exception.ResourceNotFoundException;
import com.duoc.ms_reservas.feign.ClienteClient;
import com.duoc.ms_reservas.feign.VehiculoClient;
import com.duoc.ms_reservas.mapper.ReservaMapper;
import com.duoc.ms_reservas.model.EstadoReserva;
import com.duoc.ms_reservas.model.Reserva;
import com.duoc.ms_reservas.repository.EstadoReservaRepository;
import com.duoc.ms_reservas.repository.ReservaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private EstadoReservaRepository estadoReservaRepository;

    @Mock
    private ReservaMapper reservaMapper;

    @Mock
    private ClienteClient clienteClient;

    @Mock
    private VehiculoClient vehiculoClient;

    @InjectMocks
    private ReservaService reservaService;

    @Test
    @DisplayName("Debe crear una reserva con exito cuando cliente, vehiculo disponible y estado existen")
    void save_WhenAllValid_ReturnsReservaDTO() {
        ReservaRequestDTO request = new ReservaRequestDTO(
                1, 10, LocalDate.now(), LocalDate.now().plusDays(5),
                5, new BigDecimal("150000"), "Reserva de prueba", true, 1
        );

        ClienteDTO clienteMock = new ClienteDTO();
        clienteMock.setId(1);

        VehiculoDTO vehiculoMock = new VehiculoDTO();
        vehiculoMock.setId(10);
        vehiculoMock.setDisponible(true);

        EstadoReserva estadoMock = new EstadoReserva();
        estadoMock.setId(1);

        Reserva entidadAntesGuardar = new Reserva();
        Reserva entidadGuardada = new Reserva();
        entidadGuardada.setId(100);

        ReservaDTO dtoResultadoMock = new ReservaDTO();
        dtoResultadoMock.setId(100);

        Mockito.when(clienteClient.findById(1)).thenReturn(clienteMock);
        Mockito.when(vehiculoClient.findById(10)).thenReturn(vehiculoMock);
        Mockito.when(estadoReservaRepository.findById(1)).thenReturn(Optional.of(estadoMock));
        Mockito.when(reservaMapper.toEntity(request, estadoMock)).thenReturn(entidadAntesGuardar);
        Mockito.when(reservaRepository.save(entidadAntesGuardar)).thenReturn(entidadGuardada);
        Mockito.when(reservaMapper.toDTO(entidadGuardada)).thenReturn(dtoResultadoMock);

        ReservaDTO resultado = reservaService.save(request);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(100, resultado.getId());

        Mockito.verify(clienteClient, Mockito.times(1)).findById(1);
        Mockito.verify(vehiculoClient, Mockito.times(1)).findById(10);
        Mockito.verify(reservaRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Debe lanzar IllegalArgumentException cuando la fecha de fin sea anterior a la de inicio")
    void save_WhenFechasInconsistentes_ThrowsIllegalArgumentException() {
        ReservaRequestDTO request = new ReservaRequestDTO();
        request.setFechaInicio(LocalDate.now().plusDays(5));
        request.setFechaFin(LocalDate.now());

        Assertions.assertThrows(IllegalArgumentException.class, () -> reservaService.save(request));

        Mockito.verifyNoInteractions(clienteClient, vehiculoClient, reservaRepository);
    }

    @Test
    @DisplayName("Debe lanzar IllegalStateException cuando el vehiculo no este disponible")
    void save_WhenVehiculoNotDisponible_ThrowsIllegalStateException() {
        ReservaRequestDTO request = new ReservaRequestDTO(
                1, 10, LocalDate.now(), LocalDate.now().plusDays(2),
                2, new BigDecimal("50000"), "Prueba", true, 1
        );

        ClienteDTO clienteMock = new ClienteDTO();
        clienteMock.setId(1);

        VehiculoDTO vehiculoMock = new VehiculoDTO();
        vehiculoMock.setId(10);
        vehiculoMock.setDisponible(false);

        Mockito.when(clienteClient.findById(1)).thenReturn(clienteMock);
        Mockito.when(vehiculoClient.findById(10)).thenReturn(vehiculoMock);

        Assertions.assertThrows(IllegalStateException.class, () -> reservaService.save(request));

        Mockito.verify(estadoReservaRepository, Mockito.never()).findById(Mockito.anyInt());
        Mockito.verify(reservaRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    @DisplayName("Debe retornar todas las reservas mapeadas")
    void findAll_ReturnsReservaDTOList() {
        Reserva reserva = new Reserva();
        reserva.setId(1);

        ReservaDTO reservaDTO = new ReservaDTO();
        reservaDTO.setId(1);

        Mockito.when(reservaRepository.findAll()).thenReturn(List.of(reserva));
        Mockito.when(reservaMapper.toDTO(reserva)).thenReturn(reservaDTO);

        List<ReservaDTO> resultado = reservaService.findAll();

        Assertions.assertEquals(1, resultado.size());
        Assertions.assertEquals(1, resultado.get(0).getId());
        Mockito.verify(reservaRepository).findAll();
    }

    @Test
    @DisplayName("Debe retornar una reserva al buscar por un ID existente")
    void findById_WhenExists_ReturnsReservaDTO() {
        Reserva reserva = new Reserva();
        reserva.setId(1);

        ReservaDTO reservaDTO = new ReservaDTO();
        reservaDTO.setId(1);

        Mockito.when(reservaRepository.findById(1)).thenReturn(Optional.of(reserva));
        Mockito.when(reservaMapper.toDTO(reserva)).thenReturn(reservaDTO);

        ReservaDTO resultado = reservaService.findById(1);

        Assertions.assertEquals(1, resultado.getId());
    }

    @Test
    @DisplayName("Debe lanzar excepcion cuando no existe la reserva buscada")
    void findById_WhenNotExists_ThrowsResourceNotFoundException() {
        Mockito.when(reservaRepository.findById(99)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> reservaService.findById(99));
    }

    @Test
    @DisplayName("Debe eliminar una reserva existente")
    void delete_WhenExists_DeletesReserva() {
        Reserva reserva = new Reserva();
        reserva.setId(1);

        Mockito.when(reservaRepository.findById(1)).thenReturn(Optional.of(reserva));

        reservaService.delete(1);

        Mockito.verify(reservaRepository).delete(reserva);
    }
}