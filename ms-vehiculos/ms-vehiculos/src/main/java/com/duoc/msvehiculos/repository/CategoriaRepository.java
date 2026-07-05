package com.duoc.msvehiculos.repository;

import com.duoc.msvehiculos.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
}