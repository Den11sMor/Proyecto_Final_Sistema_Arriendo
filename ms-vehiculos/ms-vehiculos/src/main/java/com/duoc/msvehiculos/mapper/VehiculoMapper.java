package com.duoc.msvehiculos.mapper;

import com.duoc.msvehiculos.dto.VehiculoDTO;
import com.duoc.msvehiculos.dto.VehiculoRequestDTO;
import com.duoc.msvehiculos.model.Categoria;
import com.duoc.msvehiculos.model.Vehiculo;

public class VehiculoMapper {

    public static VehiculoDTO toDTO(Vehiculo vehiculo) {
        if (vehiculo == null) {
            return null;
        }

        return VehiculoDTO.builder()
                .id(vehiculo.getId())
                .patente(vehiculo.getPatente())
                .marca(vehiculo.getMarca())
                .modelo(vehiculo.getModelo())
                .anio(vehiculo.getAnio())
                .color(vehiculo.getColor())
                .precioArriendoDiario(vehiculo.getPrecioArriendoDiario())
                .kilometraje(vehiculo.getKilometraje())
                .disponible(vehiculo.getDisponible())
                .fechaIngreso(vehiculo.getFechaIngreso())
                .categoriaId(vehiculo.getCategoria().getId())
                .categoriaNombre(vehiculo.getCategoria().getNombre())
                .build();
    }

    public static Vehiculo toEntity(VehiculoRequestDTO dto, Categoria categoria) {
        if (dto == null) {
            return null;
        }

        return Vehiculo.builder()
                .patente(dto.getPatente())
                .marca(dto.getMarca())
                .modelo(dto.getModelo())
                .anio(dto.getAnio())
                .color(dto.getColor())
                .precioArriendoDiario(dto.getPrecioArriendoDiario())
                .kilometraje(dto.getKilometraje())
                .disponible(dto.getDisponible())
                .fechaIngreso(dto.getFechaIngreso())
                .categoria(categoria)
                .build();
    }

    // agregado: actualiza campo por campo para el PUT
    public static void updateEntity(Vehiculo vehiculo, VehiculoRequestDTO dto, Categoria categoria) {
        vehiculo.setPatente(dto.getPatente());
        vehiculo.setMarca(dto.getMarca());
        vehiculo.setModelo(dto.getModelo());
        vehiculo.setAnio(dto.getAnio());
        vehiculo.setColor(dto.getColor());
        vehiculo.setPrecioArriendoDiario(dto.getPrecioArriendoDiario());
        vehiculo.setKilometraje(dto.getKilometraje());
        vehiculo.setDisponible(dto.getDisponible());
        vehiculo.setFechaIngreso(dto.getFechaIngreso());
        vehiculo.setCategoria(categoria);
    }
}