package com.duoc.ms_reservas.repository;

import com.duoc.ms_reservas.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * Repositorio JPA encargado de acceder a los datos de reservas.
 */
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

    /**
     * Busca reservas cuya fecha de inicio sea igual o posterior a la fecha indicada.
     *
     * @param fecha fecha minima para realizar la busqueda
     * @return lista de reservas encontradas ordenadas por fecha de inicio descendente
     */
    @Query("SELECT r FROM Reserva r WHERE r.fechaInicio >= :fecha ORDER BY r.fechaInicio DESC")
    List<Reserva> buscarReservasDesdeFecha(@Param("fecha") LocalDate fecha);
}