package com.duoc.ms_clientes.repository;

import com.duoc.ms_clientes.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    List<Cliente> findByEmailContainingIgnoreCase(String texto);
    // CAMBIO: sirve para buscar cliente exacto por email en el Runner
    Optional<Cliente> findByEmail(String email);

    //
}
