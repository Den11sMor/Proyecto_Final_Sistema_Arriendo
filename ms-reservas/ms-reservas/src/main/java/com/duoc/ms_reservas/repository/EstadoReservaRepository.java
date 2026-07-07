package com.duoc.ms_reservas.repository;

import com.duoc.ms_reservas.model.EstadoReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA encargado de acceder a los datos de estados de reserva.
 */
@Repository
public interface EstadoReservaRepository extends JpaRepository<EstadoReserva, Integer> {
}