package com.duoc.ms_reservas.repository;

import com.duoc.ms_reservas.model.EstadoReserva;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class EstadoReservaRepositoryTest {

    @Autowired
    private EstadoReservaRepository estadoReservaRepository;

    @Test
    @DisplayName("Debe guardar y buscar estado por id")
    void debeGuardarYBuscarEstadoPorId() {
        EstadoReserva estado = estadoReservaRepository.save(crearEstado("Pendiente", 1));

        Optional<EstadoReserva> encontrado = estadoReservaRepository.findById(estado.getId());

        assertTrue(encontrado.isPresent());
        assertEquals("Pendiente", encontrado.get().getNombre());
    }

    @Test
    @DisplayName("Debe listar estados")
    void debeListarEstados() {
        estadoReservaRepository.save(crearEstado("Confirmada", 2));
        estadoReservaRepository.save(crearEstado("Cancelada", 3));

        assertEquals(2, estadoReservaRepository.findAll().size());
    }

    @Test
    @DisplayName("Debe eliminar estado")
    void debeEliminarEstado() {
        EstadoReserva estado = estadoReservaRepository.save(crearEstado("Finalizada", 5));

        estadoReservaRepository.deleteById(estado.getId());

        assertFalse(estadoReservaRepository.findById(estado.getId()).isPresent());
    }

    private EstadoReserva crearEstado(String nombre, Integer prioridad) {
        EstadoReserva estado = new EstadoReserva();
        estado.setNombre(nombre);
        estado.setDescripcion("Estado de prueba");
        estado.setPrioridad(prioridad);
        estado.setActivo(true);
        estado.setFechaCreacion(LocalDateTime.now());
        return estado;
    }
}
