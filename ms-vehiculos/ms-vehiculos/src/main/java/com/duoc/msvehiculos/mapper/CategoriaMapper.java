package com.duoc.msvehiculos.mapper;

import com.duoc.msvehiculos.dto.CategoriaDTO;
import com.duoc.msvehiculos.dto.CategoriaRequestDTO;
import com.duoc.msvehiculos.model.Categoria;

/**
 * Convierte entidades Categoria hacia DTO y desde DTO hacia entidad.
 */
public class CategoriaMapper {

    private CategoriaMapper() {
    }

    public static CategoriaDTO toDTO(Categoria categoria) {
        if (categoria == null) {
            return null;
        }

        return CategoriaDTO.builder()
                .id(categoria.getId())
                .nombre(categoria.getNombre())
                .descripcion(categoria.getDescripcion())
                .tarifaBase(categoria.getTarifaBase())
                .capacidadPasajeros(categoria.getCapacidadPasajeros())
                .activa(categoria.getActiva())
                .fechaCreacion(categoria.getFechaCreacion())
                .build();
    }

    public static Categoria toEntity(CategoriaRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        return Categoria.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .tarifaBase(dto.getTarifaBase())
                .capacidadPasajeros(dto.getCapacidadPasajeros())
                .activa(dto.getActiva())
                .fechaCreacion(dto.getFechaCreacion())
                .build();
    }

    public static void updateEntity(Categoria categoria, CategoriaRequestDTO dto) {
        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());
        categoria.setTarifaBase(dto.getTarifaBase());
        categoria.setCapacidadPasajeros(dto.getCapacidadPasajeros());
        categoria.setActiva(dto.getActiva());
        categoria.setFechaCreacion(dto.getFechaCreacion());
    }
}