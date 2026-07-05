package com.duoc.msvehiculos.runner;

import com.duoc.msvehiculos.model.Categoria;
import com.duoc.msvehiculos.model.Vehiculo;
import com.duoc.msvehiculos.repository.CategoriaRepository;
import com.duoc.msvehiculos.repository.VehiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final CategoriaRepository categoriaRepository;
    private final VehiculoRepository vehiculoRepository;

    @Override
    public void run(String... args) {


        if (categoriaRepository.count() == 0) {

            Categoria categoriaEconomico = Categoria.builder()
                    .nombre("Económico")
                    .descripcion("Vehículos pequeños de bajo consumo")
                    .tarifaBase(new BigDecimal("25000"))
                    .capacidadPasajeros(5)
                    .activa(true)
                    .fechaCreacion(LocalDate.now())
                    .build();

            Categoria categoriaSuv = Categoria.builder()
                    .nombre("SUV")
                    .descripcion("Vehículos familiares con mayor espacio")
                    .tarifaBase(new BigDecimal("45000"))
                    .capacidadPasajeros(5)
                    .activa(true)
                    .fechaCreacion(LocalDate.now())
                    .build();

            Categoria categoriaPremium = Categoria.builder()
                    .nombre("Premium")
                    .descripcion("Vehículos de gama alta para arriendo especial")
                    .tarifaBase(new BigDecimal("70000"))
                    .capacidadPasajeros(5)
                    .activa(true)
                    .fechaCreacion(LocalDate.now())
                    .build();

            categoriaRepository.save(categoriaEconomico);
            categoriaRepository.save(categoriaSuv);
            categoriaRepository.save(categoriaPremium);
        }

        if (vehiculoRepository.count() == 0) {

            Categoria economico = categoriaRepository.findAll().get(0);
            Categoria suv = categoriaRepository.findAll().get(1);
            Categoria premium = categoriaRepository.findAll().get(2);

            Vehiculo vehiculo1 = Vehiculo.builder()
                    .patente("ABCD12")
                    .marca("Toyota")
                    .modelo("Yaris")
                    .anio(2022)
                    .color("Blanco")
                    .precioArriendoDiario(new BigDecimal("28000"))
                    .kilometraje(25000)
                    .disponible(true)
                    .fechaIngreso(LocalDate.now())
                    .categoria(economico)
                    .build();

            Vehiculo vehiculo2 = Vehiculo.builder()
                    .patente("EFGH34")
                    .marca("Hyundai")
                    .modelo("Tucson")
                    .anio(2023)
                    .color("Gris")
                    .precioArriendoDiario(new BigDecimal("48000"))
                    .kilometraje(18000)
                    .disponible(true)
                    .fechaIngreso(LocalDate.now())
                    .categoria(suv)
                    .build();

            Vehiculo vehiculo3 = Vehiculo.builder()
                    .patente("IJKL56")
                    .marca("BMW")
                    .modelo("Serie 3")
                    .anio(2021)
                    .color("Negro")
                    .precioArriendoDiario(new BigDecimal("75000"))
                    .kilometraje(32000)
                    .disponible(false)
                    .fechaIngreso(LocalDate.now())
                    .categoria(premium)
                    .build();

            vehiculoRepository.save(vehiculo1);
            vehiculoRepository.save(vehiculo2);
            vehiculoRepository.save(vehiculo3);
        }
    }
}
