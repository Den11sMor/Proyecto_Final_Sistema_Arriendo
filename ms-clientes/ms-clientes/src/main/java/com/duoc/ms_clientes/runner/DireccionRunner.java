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

/**
 * Carga datos iniciales de direcciones cuando la tabla esta vacia.
 */
@Component
@RequiredArgsConstructor
@Order(2)
public class DireccionRunner implements CommandLineRunner {

    private final DireccionRepository direccionRepository;
    private final ClienteRepository clienteRepository;

    @Override
    public void run(String... args) {
        if (direccionRepository.count() == 0) {
            Cliente cliente1 = clienteRepository.findByEmail("juan.perez@gmail.com")
                    .orElseThrow(() -> new RuntimeException("Cliente Juan no encontrado"));

            Cliente cliente2 = clienteRepository.findByEmail("maria.gonzalez@gmail.com")
                    .orElseThrow(() -> new RuntimeException("Cliente Maria no encontrado"));

            Cliente cliente3 = clienteRepository.findByEmail("pedro.soto@hotmail.com")
                    .orElseThrow(() -> new RuntimeException("Cliente Pedro no encontrado"));

            Cliente cliente4 = clienteRepository.findByEmail("ana.rojas@gmail.com")
                    .orElseThrow(() -> new RuntimeException("Cliente Ana no encontrado"));

            Cliente cliente5 = clienteRepository.findByEmail("luis.torres@gmail.com")
                    .orElseThrow(() -> new RuntimeException("Cliente Luis no encontrado"));

            Direccion direccion1 = new Direccion();
            direccion1.setCalle("Av Providencia");
            direccion1.setNumero(123);
            direccion1.setComuna("Providencia");
            direccion1.setCiudad("Santiago");
            direccion1.setReferencia("Cerca del metro");
            direccion1.setPrincipal(true);
            direccion1.setFechaRegistro(LocalDate.now());
            direccion1.setCliente(cliente1);

            Direccion direccion2 = new Direccion();
            direccion2.setCalle("Av Grecia");
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

            Direccion direccion4 = new Direccion();
            direccion4.setCalle("Av Las Condes");
            direccion4.setNumero(1450);
            direccion4.setComuna("Las Condes");
            direccion4.setCiudad("Santiago");
            direccion4.setReferencia("Edificio principal");
            direccion4.setPrincipal(true);
            direccion4.setFechaRegistro(LocalDate.now());
            direccion4.setCliente(cliente4);

            Direccion direccion5 = new Direccion();
            direccion5.setCalle("Av Vina del Mar");
            direccion5.setNumero(220);
            direccion5.setComuna("Centro");
            direccion5.setCiudad("Vina del Mar");
            direccion5.setReferencia("Cerca de terminal");
            direccion5.setPrincipal(true);
            direccion5.setFechaRegistro(LocalDate.now());
            direccion5.setCliente(cliente5);

            direccionRepository.save(direccion1);
            direccionRepository.save(direccion2);
            direccionRepository.save(direccion3);
            direccionRepository.save(direccion4);
            direccionRepository.save(direccion5);
        }
    }
}