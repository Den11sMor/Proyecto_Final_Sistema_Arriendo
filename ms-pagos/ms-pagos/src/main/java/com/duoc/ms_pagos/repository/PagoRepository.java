package com.duoc.ms_pagos.repository;

import com.duoc.ms_pagos.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * Repositorio JPA encargado de acceder a los datos de pagos.
 */
public interface PagoRepository extends JpaRepository<Pago, Integer> {

    /**
     * Busca pagos dentro de un rango de monto.
     *
     * @param min monto minimo
     * @param max monto maximo
     * @return lista de pagos encontrados
     */
    @Query("SELECT p FROM Pago p WHERE p.monto BETWEEN :min AND :max ORDER BY p.fechaPago DESC")
    List<Pago> buscarPagosPorRangoMonto(
            @Param("min") BigDecimal min,
            @Param("max") BigDecimal max
    );
}