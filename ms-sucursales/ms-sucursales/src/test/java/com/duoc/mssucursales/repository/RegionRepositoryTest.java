package com.duoc.mssucursales.repository;

import com.duoc.mssucursales.model.Region;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class RegionRepositoryTest {

    @Autowired
    private RegionRepository regionRepository;

    @Test
    @DisplayName("Debe guardar y buscar region por id")
    void debeGuardarYBuscarRegionPorId() {
        Region region = regionRepository.save(crearRegion("Region Metropolitana", "RM", 13));

        Optional<Region> encontrada = regionRepository.findById(region.getId());

        assertTrue(encontrada.isPresent());
        assertEquals("RM", encontrada.get().getCodigo());
    }

    @Test
    @DisplayName("Debe buscar region por codigo")
    void debeBuscarRegionPorCodigo() {
        regionRepository.save(crearRegion("Region de Valparaiso", "V", 5));

        Optional<Region> encontrada = regionRepository.findByCodigo("V");

        assertTrue(encontrada.isPresent());
        assertEquals("Region de Valparaiso", encontrada.get().getNombre());
    }

    @Test
    @DisplayName("Debe eliminar region")
    void debeEliminarRegion() {
        Region region = regionRepository.save(crearRegion("Region del Biobio", "VIII", 8));

        regionRepository.deleteById(region.getId());

        assertFalse(regionRepository.findById(region.getId()).isPresent());
    }

    private Region crearRegion(String nombre, String codigo, Integer numeroRegion) {
        Region region = new Region();
        region.setNombre(nombre);
        region.setCodigo(codigo);
        region.setNumeroRegion(numeroRegion);
        region.setCapitalRegional("Capital");
        region.setActiva(true);
        region.setFechaCreacion(LocalDate.now());
        return region;
    }
}
