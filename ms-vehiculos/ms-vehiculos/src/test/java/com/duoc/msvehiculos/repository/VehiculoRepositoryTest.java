package com.duoc.msvehiculos.repository;

import com.duoc.msvehiculos.model.Categoria;
import com.duoc.msvehiculos.model.Vehiculo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class VehiculoRepositoryTest {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Test
    @DisplayName("Debe guardar y buscar vehiculo por id")
    void debeGuardarYBuscarVehiculoPorId() {
        Categoria categoria = categoriaRepository.save(crearCategoria());
        Vehiculo vehiculo = vehiculoRepository.save(crearVehiculo("TEST01", true, new BigDecimal("25000"), categoria));

        Optional<Vehiculo> encontrado = vehiculoRepository.findById(vehiculo.getId());

        assertTrue(encontrado.isPresent());
        assertEquals("TEST01", encontrado.get().getPatente());
        assertEquals(categoria.getId(), encontrado.get().getCategoria().getId());
    }

    @Test
    @DisplayName("Debe buscar vehiculos disponibles bajo precio maximo")
    void debeBuscarDisponiblesPorPrecioMaximo() {
        Categoria categoria = categoriaRepository.save(crearCategoria());
        vehiculoRepository.save(crearVehiculo("AAAA11", true, new BigDecimal("25000"), categoria));
        vehiculoRepository.save(crearVehiculo("BBBB22", true, new BigDecimal("50000"), categoria));
        vehiculoRepository.save(crearVehiculo("CCCC33", false, new BigDecimal("20000"), categoria));

        List<Vehiculo> resultado = vehiculoRepository
                .findByDisponibleTrueAndPrecioArriendoDiarioLessThan(new BigDecimal("30000"));

        assertEquals(1, resultado.size());
        assertEquals("AAAA11", resultado.get(0).getPatente());
    }

    @Test
    @DisplayName("Debe eliminar vehiculo")
    void debeEliminarVehiculo() {
        Categoria categoria = categoriaRepository.save(crearCategoria());
        Vehiculo vehiculo = vehiculoRepository.save(crearVehiculo("DEL123", true, new BigDecimal("30000"), categoria));

        vehiculoRepository.deleteById(vehiculo.getId());

        assertFalse(vehiculoRepository.findById(vehiculo.getId()).isPresent());
    }

    private Categoria crearCategoria() {
        return Categoria.builder()
                .nombre("Economico")
                .descripcion("Categoria de prueba")
                .tarifaBase(new BigDecimal("25000"))
                .capacidadPasajeros(5)
                .activa(true)
                .fechaCreacion(LocalDate.now())
                .build();
    }

    private Vehiculo crearVehiculo(String patente, Boolean disponible, BigDecimal precio, Categoria categoria) {
        return Vehiculo.builder()
                .patente(patente)
                .marca("Toyota")
                .modelo("Yaris")
                .anio(2022)
                .color("Blanco")
                .precioArriendoDiario(precio)
                .kilometraje(20000)
                .disponible(disponible)
                .fechaIngreso(LocalDate.now())
                .categoria(categoria)
                .build();
    }
}
