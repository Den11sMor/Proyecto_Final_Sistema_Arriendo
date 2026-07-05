package com.duoc.ms_reportes.repository;

import com.duoc.ms_reportes.model.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReporteRepository extends JpaRepository<Reporte, Integer> {

    List<Reporte> findByReservaId(Integer reservaId);

    @Query("SELECT r FROM Reporte r WHERE r.pagoConfirmado = :confirmado ORDER BY r.fechaGeneracion DESC")
    List<Reporte> buscarPorPagoConfirmado(@Param("confirmado") boolean confirmado);
}
