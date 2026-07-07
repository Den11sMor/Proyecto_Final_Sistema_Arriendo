package com.duoc.msvehiculos.service;

import com.duoc.msvehiculos.dto.CategoriaDTO;
import com.duoc.msvehiculos.dto.CategoriaRequestDTO;
import com.duoc.msvehiculos.exception.ResourceNotFoundException;
import com.duoc.msvehiculos.mapper.CategoriaMapper;
import com.duoc.msvehiculos.model.Categoria;
import com.duoc.msvehiculos.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio encargado de la logica de negocio de categorias.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public List<CategoriaDTO> findAll() {
        log.info("Listando todas las categorias");

        try {
            return categoriaRepository.findAll()
                    .stream()
                    .map(CategoriaMapper::toDTO)
                    .toList();
        } catch (Exception e) {
            log.error("Error al listar categorias", e);
            throw e;
        }
    }

    public CategoriaDTO findById(Integer id) {
        log.info("Buscando categoria con id: {}", id);

        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada con id: " + id));

        return CategoriaMapper.toDTO(categoria);
    }

    public CategoriaDTO save(CategoriaRequestDTO dto) {
        log.info("Guardando nueva categoria: {}", dto.getNombre());

        try {
            Categoria categoria = CategoriaMapper.toEntity(dto);
            Categoria categoriaGuardada = categoriaRepository.save(categoria);

            return CategoriaMapper.toDTO(categoriaGuardada);
        } catch (Exception e) {
            log.error("Error al guardar categoria", e);
            throw e;
        }
    }

    public CategoriaDTO update(Integer id, CategoriaRequestDTO dto) {
        log.info("Actualizando categoria con id: {}", id);

        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada con id: " + id));

        CategoriaMapper.updateEntity(categoria, dto);

        Categoria categoriaActualizada = categoriaRepository.save(categoria);

        return CategoriaMapper.toDTO(categoriaActualizada);
    }

    public void delete(Integer id) {
        log.info("Eliminando categoria con id: {}", id);

        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada con id: " + id));

        categoriaRepository.delete(categoria);
    }
}