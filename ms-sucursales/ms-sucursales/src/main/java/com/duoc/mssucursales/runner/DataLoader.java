package com.duoc.mssucursales.runner;

import com.duoc.mssucursales.model.Region;
import com.duoc.mssucursales.model.Sucursal;
import com.duoc.mssucursales.repository.RegionRepository;
import com.duoc.mssucursales.repository.SucursalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Carga datos iniciales de regiones y sucursales cuando las tablas estan vacias
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final RegionRepository regionRepository;
    private final SucursalRepository sucursalRepository;

    @Override
    public void run(String... args) {
        if (regionRepository.count() == 0 && sucursalRepository.count() == 0) {
            log.info("Cargando datos iniciales de regiones y sucursales");

            Region regionMetropolitana = new Region();
            regionMetropolitana.setNombre("Region Metropolitana");
            regionMetropolitana.setCodigo("RM");
            regionMetropolitana.setNumeroRegion(13);
            regionMetropolitana.setCapitalRegional("Santiago");
            regionMetropolitana.setActiva(true);
            regionMetropolitana.setFechaCreacion(LocalDate.now());

            Region regionValparaiso = new Region();
            regionValparaiso.setNombre("Region de Valparaiso");
            regionValparaiso.setCodigo("V");
            regionValparaiso.setNumeroRegion(5);
            regionValparaiso.setCapitalRegional("Valparaiso");
            regionValparaiso.setActiva(true);
            regionValparaiso.setFechaCreacion(LocalDate.now());

            Region regionBiobio = new Region();
            regionBiobio.setNombre("Region del Biobio");
            regionBiobio.setCodigo("VIII");
            regionBiobio.setNumeroRegion(8);
            regionBiobio.setCapitalRegional("Concepcion");
            regionBiobio.setActiva(true);
            regionBiobio.setFechaCreacion(LocalDate.now());

            Region regionCoquimbo = new Region();
            regionCoquimbo.setNombre("Region de Coquimbo");
            regionCoquimbo.setCodigo("IV");
            regionCoquimbo.setNumeroRegion(4);
            regionCoquimbo.setCapitalRegional("La Serena");
            regionCoquimbo.setActiva(true);
            regionCoquimbo.setFechaCreacion(LocalDate.now());

            Region regionAraucania = new Region();
            regionAraucania.setNombre("Region de La Araucania");
            regionAraucania.setCodigo("IX");
            regionAraucania.setNumeroRegion(9);
            regionAraucania.setCapitalRegional("Temuco");
            regionAraucania.setActiva(true);
            regionAraucania.setFechaCreacion(LocalDate.now());

            regionRepository.save(regionMetropolitana);
            regionRepository.save(regionValparaiso);
            regionRepository.save(regionBiobio);
            regionRepository.save(regionCoquimbo);
            regionRepository.save(regionAraucania);

            Sucursal sucursalSantiago = new Sucursal();
            sucursalSantiago.setNombre("Sucursal Santiago Centro");
            sucursalSantiago.setDireccion("Av. Libertador Bernardo O'Higgins 1234");
            sucursalSantiago.setComuna("Santiago");
            sucursalSantiago.setTelefono(221234567);
            sucursalSantiago.setOperativa(true);
            sucursalSantiago.setFechaApertura(LocalDate.of(2024, 3, 15));
            sucursalSantiago.setRegion(regionMetropolitana);

            Sucursal sucursalValparaiso = new Sucursal();
            sucursalValparaiso.setNombre("Sucursal Valparaiso");
            sucursalValparaiso.setDireccion("Calle Prat 456");
            sucursalValparaiso.setComuna("Valparaiso");
            sucursalValparaiso.setTelefono(322345678);
            sucursalValparaiso.setOperativa(true);
            sucursalValparaiso.setFechaApertura(LocalDate.of(2024, 5, 10));
            sucursalValparaiso.setRegion(regionValparaiso);

            Sucursal sucursalConcepcion = new Sucursal();
            sucursalConcepcion.setNombre("Sucursal Concepcion");
            sucursalConcepcion.setDireccion("Av. Los Carrera 789");
            sucursalConcepcion.setComuna("Concepcion");
            sucursalConcepcion.setTelefono(412345678);
            sucursalConcepcion.setOperativa(true);
            sucursalConcepcion.setFechaApertura(LocalDate.of(2024, 7, 20));
            sucursalConcepcion.setRegion(regionBiobio);

            Sucursal sucursalLaSerena = new Sucursal();
            sucursalLaSerena.setNombre("Sucursal La Serena");
            sucursalLaSerena.setDireccion("Av. Francisco de Aguirre 321");
            sucursalLaSerena.setComuna("La Serena");
            sucursalLaSerena.setTelefono(512345678);
            sucursalLaSerena.setOperativa(true);
            sucursalLaSerena.setFechaApertura(LocalDate.of(2024, 8, 12));
            sucursalLaSerena.setRegion(regionCoquimbo);

            Sucursal sucursalTemuco = new Sucursal();
            sucursalTemuco.setNombre("Sucursal Temuco");
            sucursalTemuco.setDireccion("Av. Alemania 654");
            sucursalTemuco.setComuna("Temuco");
            sucursalTemuco.setTelefono(452345678);
            sucursalTemuco.setOperativa(true);
            sucursalTemuco.setFechaApertura(LocalDate.of(2024, 9, 5));
            sucursalTemuco.setRegion(regionAraucania);

            sucursalRepository.save(sucursalSantiago);
            sucursalRepository.save(sucursalValparaiso);
            sucursalRepository.save(sucursalConcepcion);
            sucursalRepository.save(sucursalLaSerena);
            sucursalRepository.save(sucursalTemuco);

            log.info("Datos iniciales de regiones y sucursales cargados correctamente");
        } else {
            log.info("Ya existen regiones o sucursales registradas no se cargan datos iniciales");
        }
    }
}
