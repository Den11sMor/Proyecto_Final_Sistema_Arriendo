package com.duoc.ms_clientes.runner;


import com.duoc.ms_clientes.model.Cliente;
import com.duoc.ms_clientes.model.Direccion;
import com.duoc.ms_clientes.repository.ClienteRepository;
import com.duoc.ms_clientes.repository.DireccionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Order(2)
public class DireccionRunner implements CommandLineRunner {

    private final DireccionRepository direccionRepository;
    private final ClienteRepository clienteRepository;

    @Override
    public void run(String... args) {
        if (direccionRepository.count() == 0) {

            Cliente cliente1 = clienteRepository.findByEmail("juan.perez@gmail.com") // CAMBIO: se busca por email
                    .orElseThrow(() -> new RuntimeException("Cliente Juan no encontrado"));

            Cliente cliente2 = clienteRepository.findByEmail("maria.gonzalez@gmail.com") // CAMBIO: se busca por email
                    .orElseThrow(() -> new RuntimeException("Cliente Maria no encontrado"));

            Cliente cliente3 = clienteRepository.findByEmail("pedro.soto@hotmail.com") // CAMBIO: se busca por email
                    .orElseThrow(() -> new RuntimeException("Cliente Pedro no encontrado"));

            Direccion direccion1 = new Direccion();
            direccion1.setCalle("Av. Providencia");
            direccion1.setNumero(123);
            direccion1.setComuna("Providencia");
            direccion1.setCiudad("Santiago");
            direccion1.setReferencia("Cerca del metro");
            direccion1.setPrincipal(true);
            direccion1.setFechaRegistro(LocalDate.now());
            direccion1.setCliente(cliente1);

            Direccion direccion2 = new Direccion();
            direccion2.setCalle("Av. Grecia");
            direccion2.setNumero(456);
            direccion2.setComuna("Nunoa");
            direccion2.setCiudad("Santiago");
            direccion2.setReferencia("Frente a supermercado");
            direccion2.setPrincipal(true);
            direccion2.setFechaRegistro(LocalDate.now());
            direccion2.setCliente(cliente2);

            Direccion direccion3 = new Direccion();
            direccion3.setCalle("Gran Avenida");
            direccion3.setNumero(789);
            direccion3.setComuna("San Miguel");
            direccion3.setCiudad("Santiago");
            direccion3.setReferencia("Cerca de plaza");
            direccion3.setPrincipal(true);
            direccion3.setFechaRegistro(LocalDate.now());
            direccion3.setCliente(cliente3);

            direccionRepository.save(direccion1);
            direccionRepository.save(direccion2);
            direccionRepository.save(direccion3);
        }
    }
}
