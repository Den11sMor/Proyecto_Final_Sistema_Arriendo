package com.duoc.ms_clientes.repository;

import com.duoc.ms_clientes.model.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA encargado de acceder a los datos de direcciones.
 */
@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Integer> {
}