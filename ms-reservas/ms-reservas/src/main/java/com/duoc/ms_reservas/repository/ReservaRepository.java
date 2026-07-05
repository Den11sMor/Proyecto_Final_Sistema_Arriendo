package com.duoc.ms_reservas.repository;


import com.duoc.ms_reservas.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

    // agregado: JPQL solicitado en la pauta para buscar reservas desde una fecha
    @Query("SELECT r FROM Reserva r WHERE r.fechaInicio >= :fecha ORDER BY r.fechaInicio DESC")
    List<Reserva> buscarReservasDesdeFecha(@Param("fecha") LocalDate fecha);
}