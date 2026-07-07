package com.duoc.mssucursales.repository;

import com.duoc.mssucursales.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para acceder a los datos de regiones
 */
@Repository
public interface RegionRepository extends JpaRepository<Region, Integer> {

    /**
     * Busca una region por su codigo unico
     */
    Optional<Region> findByCodigo(String codigo);
}