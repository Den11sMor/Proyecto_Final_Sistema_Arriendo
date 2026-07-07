package com.duoc.msvehiculos.service;

import com.duoc.msvehiculos.dto.VehiculoDTO;
import com.duoc.msvehiculos.dto.VehiculoRequestDTO;
import com.duoc.msvehiculos.exception.ResourceNotFoundException;
import com.duoc.msvehiculos.model.Categoria;
import com.duoc.msvehiculos.model.Vehiculo;
import com.duoc.msvehiculos.repository.CategoriaRepository;
import com.duoc.msvehiculos.repository.VehiculoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias del servicio de vehiculos.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("VehiculoService")
class VehiculoServiceTest {

    @Mock
    private VehiculoRepository vehiculoRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private VehiculoService vehiculoService;

    private Categoria categoria;
    private Vehiculo vehiculo;
    private VehiculoRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        categoria = Categoria.builder()
                .id(1)
                .nombre("Sedan")
                .descripcion("Vehiculos compactos")
                .tarifaBase(new BigDecimal("25000"))
                .capacidadPasajeros(5)
                .activa(true)
                .fechaCreacion(LocalDate.now())
                .build();

        vehiculo = Vehiculo.builder()
                .id(1)
                .patente("ABCD12")
                .marca("Toyota")
                .modelo("Corolla")
                .anio(2022)
                .color("Blanco")
                .precioArriendoDiario(new BigDecimal("35000"))
                .kilometraje(25000)
                .disponible(true)
                .fechaIngreso(LocalDate.now())
                .categoria(categoria)
                .build();

        requestDTO = VehiculoRequestDTO.builder()
                .patente("ABCD12")
                .marca("Toyota")
                .modelo("Corolla")
                .anio(2022)
                .color("Blanco")
                .precioArriendoDiario(new BigDecimal("35000"))
                .kilometraje(25000)
                .disponible(true)
                .fechaIngreso(LocalDate.now())
                .categoriaId(1)
                .build();
    }

    @Test
    @DisplayName("Debe listar vehiculos")
    void testFindAll() {
        when(vehiculoRepository.findAll()).thenReturn(List.of(vehiculo));

        List<VehiculoDTO> resultado = vehiculoService.findAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("ABCD12", resultado.get(0).getPatente());
        verify(vehiculoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe buscar vehiculo por id")
    void testFindById() {
        when(vehiculoRepository.findById(1)).thenReturn(Optional.of(vehiculo));

        VehiculoDTO resultado = vehiculoService.findById(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("ABCD12", resultado.getPatente());
        verify(vehiculoRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Debe lanzar error cuando vehiculo no existe")
    void testFindByIdNoEncontrado() {
        when(vehiculoRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> vehiculoService.findById(99));
        verify(vehiculoRepository, times(1)).findById(99);
    }

    @Test
    @DisplayName("Debe guardar vehiculo")
    void testSave() {
        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoria));
        when(vehiculoRepository.save(any(Vehiculo.class))).thenReturn(vehiculo);

        VehiculoDTO resultado = vehiculoService.save(requestDTO);

        assertNotNull(resultado);
        assertEquals("ABCD12", resultado.getPatente());
        verify(categoriaRepository, times(1)).findById(1);
        verify(vehiculoRepository, times(1)).save(any(Vehiculo.class));
    }

    @Test
    @DisplayName("Debe lanzar error al guardar si categoria no existe")
    void testSaveCategoriaNoEncontrada() {
        when(categoriaRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> vehiculoService.save(requestDTO));
        verify(categoriaRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Debe actualizar vehiculo")
    void testUpdate() {
        when(vehiculoRepository.findById(1)).thenReturn(Optional.of(vehiculo));
        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoria));
        when(vehiculoRepository.save(any(Vehiculo.class))).thenReturn(vehiculo);

        VehiculoDTO resultado = vehiculoService.update(1, requestDTO);

        assertNotNull(resultado);
        assertEquals("ABCD12", resultado.getPatente());
        verify(vehiculoRepository, times(1)).findById(1);
        verify(categoriaRepository, times(1)).findById(1);
        verify(vehiculoRepository, times(1)).save(vehiculo);
    }

    @Test
    @DisplayName("Debe eliminar vehiculo")
    void testDelete() {
        when(vehiculoRepository.findById(1)).thenReturn(Optional.of(vehiculo));

        vehiculoService.delete(1);

        verify(vehiculoRepository, times(1)).findById(1);
        verify(vehiculoRepository, times(1)).delete(vehiculo);
    }

    @Test
    @DisplayName("Debe buscar vehiculos disponibles por precio")
    void testBuscarDisponiblesPorPrecioMenor() {
        BigDecimal precioMaximo = new BigDecimal("50000");
        when(vehiculoRepository.findByDisponibleTrueAndPrecioArriendoDiarioLessThan(precioMaximo))
                .thenReturn(List.of(vehiculo));

        List<VehiculoDTO> resultado = vehiculoService.buscarDisponiblesPorPrecioMenor(precioMaximo);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("ABCD12", resultado.get(0).getPatente());
        verify(vehiculoRepository, times(1)).findByDisponibleTrueAndPrecioArriendoDiarioLessThan(precioMaximo);
    }
}
