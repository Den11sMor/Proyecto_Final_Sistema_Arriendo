package com.duoc.ms_clientes.repository;

import com.duoc.ms_clientes.model.Cliente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    @DisplayName("Debe guardar y buscar cliente por id")
    void debeGuardarYBuscarClientePorId() {
        Cliente cliente = crearCliente("12345678-9", "Juan", "Perez", "juan.perez@test.com");

        Cliente guardado = clienteRepository.save(cliente);
        Optional<Cliente> encontrado = clienteRepository.findById(guardado.getId());

        assertTrue(encontrado.isPresent());
        assertEquals("Juan", encontrado.get().getNombre());
        assertEquals("juan.perez@test.com", encontrado.get().getEmail());
    }

    @Test
    @DisplayName("Debe buscar cliente por email exacto")
    void debeBuscarClientePorEmail() {
        Cliente cliente = crearCliente("11222333-4", "Maria", "Gonzalez", "maria.gonzalez@test.com");
        clienteRepository.save(cliente);

        Optional<Cliente> encontrado = clienteRepository.findByEmail("maria.gonzalez@test.com");

        assertTrue(encontrado.isPresent());
        assertEquals("Maria", encontrado.get().getNombre());
    }

    @Test
    @DisplayName("Debe buscar clientes por texto dentro del email ignorando mayusculas")
    void debeBuscarClientesPorEmailContainingIgnoreCase() {
        clienteRepository.save(crearCliente("99888777-6", "Pedro", "Soto", "pedro.soto@test.com"));
        clienteRepository.save(crearCliente("77666555-4", "Ana", "Rojas", "ana.rojas@test.com"));

        List<Cliente> resultado = clienteRepository.findByEmailContainingIgnoreCase("TEST");

        assertEquals(2, resultado.size());
    }

    @Test
    @DisplayName("Debe eliminar cliente")
    void debeEliminarCliente() {
        Cliente cliente = clienteRepository.save(crearCliente("66555444-3", "Luis", "Torres", "luis.torres@test.com"));

        clienteRepository.deleteById(cliente.getId());

        assertFalse(clienteRepository.findById(cliente.getId()).isPresent());
    }

    private Cliente crearCliente(String rut, String nombre, String apellido, String email) {
        Cliente cliente = new Cliente();
        cliente.setRut(rut);
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setEmail(email);
        cliente.setTelefono(987654321);
        cliente.setActivo(true);
        cliente.setFechaRegistro(LocalDate.now());
        return cliente;
    }
}
