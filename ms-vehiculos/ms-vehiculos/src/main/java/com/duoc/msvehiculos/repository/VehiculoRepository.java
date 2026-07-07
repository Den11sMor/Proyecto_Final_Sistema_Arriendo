package com.duoc.msvehiculos.repository;

import com.duoc.msvehiculos.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Repositorio JPA encargado de acceder a los datos de vehiculos.
 */
@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Integer> {

    /**
     * Busca vehiculos disponibles cuyo precio diario sea menor al valor indicado.
     *
     * @param precioMaximo precio maximo usado para filtrar
     * @return lista de vehiculos disponibles encontrados
     */
    List<Vehiculo> findByDisponibleTrueAndPrecioArriendoDiarioLessThan(BigDecimal precioMaximo);
}