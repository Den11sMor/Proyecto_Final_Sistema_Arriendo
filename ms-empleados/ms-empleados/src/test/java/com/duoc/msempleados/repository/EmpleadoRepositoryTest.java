package com.duoc.msempleados.repository;

import com.duoc.msempleados.model.Empleado;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("EmpleadoRepository")
class EmpleadoRepositoryTest {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Test
    @DisplayName("Debe guardar y buscar un empleado por id")
    void debeGuardarYBuscarEmpleadoPorId() {
        Empleado empleadoGuardado = empleadoRepository.save(crearEmpleado(
                "12345678-9",
                "Carlos Perez",
                "carlos.perez@empresa.cl",
                true,
                LocalDate.of(2024, 3, 10)
        ));

        Empleado encontrado = empleadoRepository.findById(empleadoGuardado.getId()).orElseThrow();

        assertThat(encontrado.getNombre()).isEqualTo("Carlos Perez");
        assertThat(encontrado.getActivo()).isTrue();
    }

    @Test
    @DisplayName("Debe listar empleados activos por anio")
    void debeListarEmpleadosActivosPorAnio() {
        empleadoRepository.save(crearEmpleado(
                "12345678-9",
                "Carlos Perez",
                "carlos.perez@empresa.cl",
                true,
                LocalDate.of(2024, 3, 10)
        ));
        empleadoRepository.save(crearEmpleado(
                "18765432-1",
                "Maria Gonzalez",
                "maria.gonzalez@empresa.cl",
                true,
                LocalDate.of(2024, 6, 15)
        ));
        empleadoRepository.save(crearEmpleado(
                "16543210-5",
                "Jorge Ramirez",
                "jorge.ramirez@empresa.cl",
                false,
                LocalDate.of(2024, 9, 20)
        ));

        List<Empleado> empleados = empleadoRepository.listarEmpleadosActivosPorAnio(2024);

        assertThat(empleados).hasSize(2);
        assertThat(empleados)
                .extracting(Empleado::getNombre)
                .containsExactlyInAnyOrder("Carlos Perez", "Maria Gonzalez");
    }

    private Empleado crearEmpleado(String rut, String nombre, String email, Boolean activo, LocalDate fechaIngreso) {
        Empleado empleado = new Empleado();
        empleado.setRut(rut);
        empleado.setNombre(nombre);
        empleado.setCargo("Ejecutivo de arriendo");
        empleado.setEmail(email);
        empleado.setSueldo(new BigDecimal("750000"));
        empleado.setActivo(activo);
        empleado.setFechaIngreso(fechaIngreso);
        return empleado;
    }
}
