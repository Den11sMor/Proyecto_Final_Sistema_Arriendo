package com.duoc.mssucursales.runner;

import com.duoc.mssucursales.model.Region;
import com.duoc.mssucursales.model.Sucursal;
import com.duoc.mssucursales.repository.RegionRepository;
import com.duoc.mssucursales.repository.SucursalRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final RegionRepository regionRepository;
    private final SucursalRepository sucursalRepository;

    public DataLoader(RegionRepository regionRepository, SucursalRepository sucursalRepository) {
        this.regionRepository = regionRepository;
        this.sucursalRepository = sucursalRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (regionRepository.count() == 0 && sucursalRepository.count() == 0) {

            Region regionMetropolitana = new Region();
            regionMetropolitana.setNombre("Región Metropolitana");
            regionMetropolitana.setCodigo("RM");
            regionMetropolitana.setNumeroRegion(13);
            regionMetropolitana.setCapitalRegional("Santiago");
            regionMetropolitana.setActiva(true);
            regionMetropolitana.setFechaCreacion(LocalDate.now());

            Region regionValparaiso = new Region();
            regionValparaiso.setNombre("Región de Valparaíso");
            regionValparaiso.setCodigo("V");
            regionValparaiso.setNumeroRegion(5);
            regionValparaiso.setCapitalRegional("Valparaíso");
            regionValparaiso.setActiva(true);
            regionValparaiso.setFechaCreacion(LocalDate.now());

            Region regionBiobio = new Region();
            regionBiobio.setNombre("Región del Biobío");
            regionBiobio.setCodigo("VIII");
            regionBiobio.setNumeroRegion(8);
            regionBiobio.setCapitalRegional("Concepción");
            regionBiobio.setActiva(true);
            regionBiobio.setFechaCreacion(LocalDate.now());

            regionRepository.save(regionMetropolitana);
            regionRepository.save(regionValparaiso);
            regionRepository.save(regionBiobio);

            Sucursal sucursalSantiago = new Sucursal();
            sucursalSantiago.setNombre("Sucursal Santiago Centro");
            sucursalSantiago.setDireccion("Av. Libertador Bernardo O'Higgins 1234");
            sucursalSantiago.setComuna("Santiago");
            sucursalSantiago.setTelefono(221234567);
            sucursalSantiago.setOperativa(true);
            sucursalSantiago.setFechaApertura(LocalDate.of(2024, 3, 15));
            sucursalSantiago.setRegion(regionMetropolitana);

            Sucursal sucursalValparaiso = new Sucursal();
            sucursalValparaiso.setNombre("Sucursal Valparaíso");
            sucursalValparaiso.setDireccion("Calle Prat 456");
            sucursalValparaiso.setComuna("Valparaíso");
            sucursalValparaiso.setTelefono(322345678);
            sucursalValparaiso.setOperativa(true);
            sucursalValparaiso.setFechaApertura(LocalDate.of(2024, 5, 10));
            sucursalValparaiso.setRegion(regionValparaiso);

            Sucursal sucursalConcepcion = new Sucursal();
            sucursalConcepcion.setNombre("Sucursal Concepción");
            sucursalConcepcion.setDireccion("Av. Los Carrera 789");
            sucursalConcepcion.setComuna("Concepción");
            sucursalConcepcion.setTelefono(412345678);
            sucursalConcepcion.setOperativa(true);
            sucursalConcepcion.setFechaApertura(LocalDate.of(2024, 7, 20));
            sucursalConcepcion.setRegion(regionBiobio);

            sucursalRepository.save(sucursalSantiago);
            sucursalRepository.save(sucursalValparaiso);
            sucursalRepository.save(sucursalConcepcion);
        }
    }

}
