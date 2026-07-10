package com.duoc.msvehiculos.repository;

import com.duoc.msvehiculos.model.Categoria;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class CategoriaRepositoryTest {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Test
    @DisplayName("Debe guardar y buscar categoria por id")
    void debeGuardarYBuscarCategoriaPorId() {
        Categoria categoria = categoriaRepository.save(crearCategoria("Economico"));

        Optional<Categoria> encontrada = categoriaRepository.findById(categoria.getId());

        assertTrue(encontrada.isPresent());
        assertEquals("Economico", encontrada.get().getNombre());
    }

    @Test
    @DisplayName("Debe listar categorias")
    void debeListarCategorias() {
        categoriaRepository.save(crearCategoria("SUV"));
        categoriaRepository.save(crearCategoria("Premium"));

        assertEquals(2, categoriaRepository.findAll().size());
    }

    @Test
    @DisplayName("Debe eliminar categoria")
    void debeEliminarCategoria() {
        Categoria categoria = categoriaRepository.save(crearCategoria("Carga"));

        categoriaRepository.deleteById(categoria.getId());

        assertFalse(categoriaRepository.findById(categoria.getId()).isPresent());
    }

    private Categoria crearCategoria(String nombre) {
        return Categoria.builder()
                .nombre(nombre)
                .descripcion("Categoria de prueba")
                .tarifaBase(new BigDecimal("30000"))
                .capacidadPasajeros(5)
                .activa(true)
                .fechaCreacion(LocalDate.now())
                .build();
    }
}
