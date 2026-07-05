package com.duoc.ms_clientes.runner;

import com.duoc.ms_clientes.model.Cliente;
import com.duoc.ms_clientes.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Order(1)
public class ClienteRunner implements CommandLineRunner {

    private final ClienteRepository clienteRepository;

    @Override
    public void run(String... args) {
        if (clienteRepository.count() == 0) {

            Cliente cliente1 = new Cliente();
            cliente1.setRut("12345678-9");
            cliente1.setNombre("Juan");
            cliente1.setApellido("Perez");
            cliente1.setEmail("juan.perez@gmail.com");
            cliente1.setTelefono(987654321);
            cliente1.setActivo(true);
            cliente1.setFechaRegistro(LocalDate.now());

            Cliente cliente2 = new Cliente();
            cliente2.setRut("11222333-4");
            cliente2.setNombre("Maria");
            cliente2.setApellido("Gonzalez");
            cliente2.setEmail("maria.gonzalez@gmail.com");
            cliente2.setTelefono(912345678);
            cliente2.setActivo(true);
            cliente2.setFechaRegistro(LocalDate.now());

            Cliente cliente3 = new Cliente();
            cliente3.setRut("99888777-6");
            cliente3.setNombre("Pedro");
            cliente3.setApellido("Soto");
            cliente3.setEmail("pedro.soto@hotmail.com");
            cliente3.setTelefono(976543210);
            cliente3.setActivo(true);
            cliente3.setFechaRegistro(LocalDate.now());

            clienteRepository.save(cliente1);
            clienteRepository.save(cliente2);
            clienteRepository.save(cliente3);
        }
    }
}
