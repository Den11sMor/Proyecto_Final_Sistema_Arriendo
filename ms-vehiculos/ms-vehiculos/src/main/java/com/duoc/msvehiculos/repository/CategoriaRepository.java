package com.duoc.msvehiculos.repository;

import com.duoc.msvehiculos.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA encargado de acceder a los datos de categorias.
 */
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
}