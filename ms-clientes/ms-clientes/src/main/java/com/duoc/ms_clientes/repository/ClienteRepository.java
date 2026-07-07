package com.duoc.ms_clientes.repository;

import com.duoc.ms_clientes.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA encargado de acceder a los datos de clientes.
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    /**
     * Busca clientes cuyo email contenga el texto indicado, sin distinguir mayusculas o minusculas.
     *
     * @param texto texto usado para buscar dentro del email
     * @return lista de clientes encontrados
     */
    List<Cliente> findByEmailContainingIgnoreCase(String texto);

    /**
     * Busca un cliente por email exacto.
     *
     * @param email email del cliente
     * @return cliente encontrado si existe
     */
    Optional<Cliente> findByEmail(String email);
}
