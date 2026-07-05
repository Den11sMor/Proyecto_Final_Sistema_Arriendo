package com.duoc.msvehiculos.service;

import com.duoc.msvehiculos.dto.CategoriaDTO;
import com.duoc.msvehiculos.dto.CategoriaRequestDTO;
import com.duoc.msvehiculos.exception.ResourceNotFoundException;
import com.duoc.msvehiculos.model.Categoria;
import com.duoc.msvehiculos.repository.CategoriaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    private Categoria categoria;
    private CategoriaRequestDTO requestDTO;

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

        requestDTO = CategoriaRequestDTO.builder()
                .nombre("Sedan")
                .descripcion("Vehiculos compactos")
                .tarifaBase(new BigDecimal("25000"))
                .capacidadPasajeros(5)
                .activa(true)
                .fechaCreacion(LocalDate.now())
                .build();
    }

    @Test
    void testFindAll() {
        when(categoriaRepository.findAll()).thenReturn(List.of(categoria));

        List<CategoriaDTO> resultado = categoriaService.findAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Sedan", resultado.get(0).getNombre());
        verify(categoriaRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoria));

        CategoriaDTO resultado = categoriaService.findById(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Sedan", resultado.getNombre());
        verify(categoriaRepository, times(1)).findById(1);
    }

    @Test
    void testFindByIdNoEncontrado() {
        when(categoriaRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoriaService.findById(99));
        verify(categoriaRepository, times(1)).findById(99);
    }

    @Test
    void testSave() {
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);

        CategoriaDTO resultado = categoriaService.save(requestDTO);

        assertNotNull(resultado);
        assertEquals("Sedan", resultado.getNombre());
        verify(categoriaRepository, times(1)).save(any(Categoria.class));
    }

    @Test
    void testUpdate() {
        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoria));
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);

        CategoriaDTO resultado = categoriaService.update(1, requestDTO);

        assertNotNull(resultado);
        assertEquals("Sedan", resultado.getNombre());
        verify(categoriaRepository, times(1)).findById(1);
        verify(categoriaRepository, times(1)).save(categoria);
    }

    @Test
    void testDelete() {
        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoria));

        categoriaService.delete(1);

        verify(categoriaRepository, times(1)).findById(1);
        verify(categoriaRepository, times(1)).delete(categoria);
    }
}
