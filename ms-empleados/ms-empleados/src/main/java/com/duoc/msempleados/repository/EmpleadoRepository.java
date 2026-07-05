package com.duoc.msempleados.repository;

import com.duoc.msempleados.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {

    @Query(value = "SELECT * FROM empleados WHERE activo = true AND YEAR(fecha_ingreso) = :anio", nativeQuery = true)
    List<Empleado>listarEmpleadosActivosPorAnio(@Param("anio") Integer anio);
}
