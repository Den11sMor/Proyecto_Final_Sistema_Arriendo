package com.duoc.ms_reservas.repository;

import com.duoc.ms_reservas.model.EstadoReserva;
import com.duoc.ms_reservas.model.Reserva;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class ReservaRepositoryTest {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private EstadoReservaRepository estadoReservaRepository;

    @Test
    @DisplayName("Debe guardar y buscar reserva por id")
    void debeGuardarYBuscarReservaPorId() {
        EstadoReserva estado = estadoReservaRepository.save(crearEstado());
        Reserva reserva = reservaRepository.save(crearReserva(LocalDate.of(2026, 7, 10), estado));

        Optional<Reserva> encontrada = reservaRepository.findById(reserva.getId());

        assertTrue(encontrada.isPresent());
        assertEquals(1, encontrada.get().getClienteId());
        assertEquals(estado.getId(), encontrada.get().getEstadoReserva().getId());
    }

    @Test
    @DisplayName("Debe buscar reservas desde fecha")
    void debeBuscarReservasDesdeFecha() {
        EstadoReserva estado = estadoReservaRepository.save(crearEstado());
        reservaRepository.save(crearReserva(LocalDate.of(2026, 7, 1), estado));
        reservaRepository.save(crearReserva(LocalDate.of(2026, 7, 15), estado));

        List<Reserva> resultado = reservaRepository.buscarReservasDesdeFecha(LocalDate.of(2026, 7, 10));

        assertEquals(1, resultado.size());
        assertEquals(LocalDate.of(2026, 7, 15), resultado.get(0).getFechaInicio());
    }

    @Test
    @DisplayName("Debe eliminar reserva")
    void debeEliminarReserva() {
        EstadoReserva estado = estadoReservaRepository.save(crearEstado());
        Reserva reserva = reservaRepository.save(crearReserva(LocalDate.of(2026, 7, 20), estado));

        reservaRepository.deleteById(reserva.getId());

        assertFalse(reservaRepository.findById(reserva.getId()).isPresent());
    }

    private EstadoReserva crearEstado() {
        EstadoReserva estado = new EstadoReserva();
        estado.setNombre("Confirmada");
        estado.setDescripcion("Estado de prueba");
        estado.setPrioridad(1);
        estado.setActivo(true);
        estado.setFechaCreacion(LocalDateTime.now());
        return estado;
    }

    private Reserva crearReserva(LocalDate fechaInicio, EstadoReserva estado) {
        Reserva reserva = new Reserva();
        reserva.setClienteId(1);
        reserva.setVehiculoId(1);
        reserva.setFechaInicio(fechaInicio);
        reserva.setFechaFin(fechaInicio.plusDays(2));
        reserva.setCantidadDias(3);
        reserva.setMontoTotal(new BigDecimal("90000"));
        reserva.setObservacion("Reserva de prueba");
        reserva.setActiva(true);
        reserva.setEstadoReserva(estado);
        return reserva;
    }
}
