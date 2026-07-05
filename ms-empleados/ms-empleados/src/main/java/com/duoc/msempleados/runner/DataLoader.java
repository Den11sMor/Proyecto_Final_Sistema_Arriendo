package com.duoc.msempleados.runner;

import com.duoc.msempleados.model.Empleado;
import com.duoc.msempleados.repository.EmpleadoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final EmpleadoRepository empleadoRepository;

    @Override
    public void run(String... args) throws Exception {

        if (empleadoRepository.count() == 0) {

            log.info("Cargando datos iniciales de empleados"); // agregado: log del runner

            Empleado empleado1 = new Empleado();
            empleado1.setRut("12345678-9");
            empleado1.setNombre("Carlos Perez");
            empleado1.setCargo("Ejecutivo de arriendo");
            empleado1.setEmail("carlos.perez@empresa.cl");
            empleado1.setSueldo(new BigDecimal("750000"));
            empleado1.setActivo(true);
            empleado1.setFechaIngreso(LocalDate.of(2024, 3, 10));

            Empleado empleado2 = new Empleado();
            empleado2.setRut("18765432-1");
            empleado2.setNombre("Maria Gonzalez");
            empleado2.setCargo("Administradora de sucursal");
            empleado2.setEmail("maria.gonzalez@empresa.cl");
            empleado2.setSueldo(new BigDecimal("950000"));
            empleado2.setActivo(true);
            empleado2.setFechaIngreso(LocalDate.of(2024, 6, 15));

            Empleado empleado3 = new Empleado();
            empleado3.setRut("16543210-5");
            empleado3.setNombre("Jorge Ramirez");
            empleado3.setCargo("Asistente operativo");
            empleado3.setEmail("jorge.ramirez@empresa.cl");
            empleado3.setSueldo(new BigDecimal("620000"));
            empleado3.setActivo(false);
            empleado3.setFechaIngreso(LocalDate.of(2023, 9, 20));

            empleadoRepository.save(empleado1);
            empleadoRepository.save(empleado2);
            empleadoRepository.save(empleado3);

            log.info("Datos iniciales de empleados cargados correctamente"); // agregado: log del runner

        } else {
            log.info("Ya existen empleados registrados, no se cargan datos iniciales"); // agregado: log del runner
        }
    }
}
