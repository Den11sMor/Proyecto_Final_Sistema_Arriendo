package com.duoc.mssucursales.repository;

import com.duoc.mssucursales.model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, Integer> {

    // Native Query pedida por la prueba: sucursales operativas ordenadas por nombre
    @Query(value = "SELECT * FROM sucursales WHERE operativa = true ORDER BY nombre ASC", nativeQuery = true)
    List<Sucursal> listarSucursalesOperativasOrdenadas();

}
