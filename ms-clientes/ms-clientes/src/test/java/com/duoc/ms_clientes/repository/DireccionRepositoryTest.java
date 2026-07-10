package com.duoc.ms_clientes.repository;

import com.duoc.ms_clientes.model.Cliente;
import com.duoc.ms_clientes.model.Direccion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class DireccionRepositoryTest {

    @Autowired
    private DireccionRepository direccionRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    @DisplayName("Debe guardar y buscar direccion por id")
    void debeGuardarYBuscarDireccionPorId() {
        Cliente cliente = clienteRepository.save(crearCliente());
        Direccion direccion = crearDireccion(cliente);

        Direccion guardada = direccionRepository.save(direccion);
        Optional<Direccion> encontrada = direccionRepository.findById(guardada.getId());

        assertTrue(encontrada.isPresent());
        assertEquals("Av Providencia", encontrada.get().getCalle());
        assertEquals("Santiago", encontrada.get().getCiudad());
        assertEquals(cliente.getId(), encontrada.get().getCliente().getId());
    }

    @Test
    @DisplayName("Debe listar direcciones")
    void debeListarDirecciones() {
        Cliente cliente = clienteRepository.save(crearCliente());
        direccionRepository.save(crearDireccion(cliente));

        assertFalse(direccionRepository.findAll().isEmpty());
    }

    @Test
    @DisplayName("Debe eliminar direccion")
    void debeEliminarDireccion() {
        Cliente cliente = clienteRepository.save(crearCliente());
        Direccion direccion = direccionRepository.save(crearDireccion(cliente));

        direccionRepository.deleteById(direccion.getId());

        assertFalse(direccionRepository.findById(direccion.getId()).isPresent());
    }

    private Cliente crearCliente() {
        Cliente cliente = new Cliente();
        cliente.setRut("12345678-9");
        cliente.setNombre("Juan");
        cliente.setApellido("Perez");
        cliente.setEmail("juan.perez@test.com");
        cliente.setTelefono(987654321);
        cliente.setActivo(true);
        cliente.setFechaRegistro(LocalDate.now());
        return cliente;
    }

    private Direccion crearDireccion(Cliente cliente) {
        Direccion direccion = new Direccion();
        direccion.setCalle("Av Providencia");
        direccion.setNumero(123);
        direccion.setComuna("Providencia");
        direccion.setCiudad("Santiago");
        direccion.setReferencia("Cerca del metro");
        direccion.setPrincipal(true);
        direccion.setFechaRegistro(LocalDate.now());
        direccion.setCliente(cliente);
        return direccion;
    }
}