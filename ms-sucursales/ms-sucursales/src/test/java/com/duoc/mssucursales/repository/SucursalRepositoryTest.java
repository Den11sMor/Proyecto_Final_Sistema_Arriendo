package com.duoc.mssucursales.repository;

import com.duoc.mssucursales.model.Region;
import com.duoc.mssucursales.model.Sucursal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class SucursalRepositoryTest {

    @Autowired
    private SucursalRepository sucursalRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Test
    @DisplayName("Debe guardar y buscar sucursal por id")
    void debeGuardarYBuscarSucursalPorId() {
        Region region = regionRepository.save(crearRegion());
        Sucursal sucursal = sucursalRepository.save(crearSucursal("Sucursal Centro", true, region));

        Optional<Sucursal> encontrada = sucursalRepository.findById(sucursal.getId());

        assertTrue(encontrada.isPresent());
        assertEquals("Sucursal Centro", encontrada.get().getNombre());
        assertEquals(region.getId(), encontrada.get().getRegion().getId());
    }

    @Test
    @DisplayName("Debe listar sucursales operativas ordenadas")
    void debeListarSucursalesOperativasOrdenadas() {
        Region region = regionRepository.save(crearRegion());
        sucursalRepository.save(crearSucursal("Sucursal B", true, region));
        sucursalRepository.save(crearSucursal("Sucursal A", true, region));
        sucursalRepository.save(crearSucursal("Sucursal Cerrada", false, region));

        List<Sucursal> resultado = sucursalRepository.listarSucursalesOperativasOrdenadas();

        assertEquals(2, resultado.size());
        assertEquals("Sucursal A", resultado.get(0).getNombre());
    }

    @Test
    @DisplayName("Debe eliminar sucursal")
    void debeEliminarSucursal() {
        Region region = regionRepository.save(crearRegion());
        Sucursal sucursal = sucursalRepository.save(crearSucursal("Sucursal Norte", true, region));

        sucursalRepository.deleteById(sucursal.getId());

        assertFalse(sucursalRepository.findById(sucursal.getId()).isPresent());
    }

    private Region crearRegion() {
        Region region = new Region();
        region.setNombre("Region Metropolitana");
        region.setCodigo("RM");
        region.setNumeroRegion(13);
        region.setCapitalRegional("Santiago");
        region.setActiva(true);
        region.setFechaCreacion(LocalDate.now());
        return region;
    }

    private Sucursal crearSucursal(String nombre, Boolean operativa, Region region) {
        Sucursal sucursal = new Sucursal();
        sucursal.setNombre(nombre);
        sucursal.setDireccion("Direccion de prueba");
        sucursal.setComuna("Santiago");
        sucursal.setTelefono(221234567);
        sucursal.setOperativa(operativa);
        sucursal.setFechaApertura(LocalDate.now());
        sucursal.setRegion(region);
        return sucursal;
    }
}
