package com.duoc.msvehiculos.service;

import com.duoc.msvehiculos.dto.VehiculoDTO;
import com.duoc.msvehiculos.dto.VehiculoRequestDTO;
import com.duoc.msvehiculos.exception.ResourceNotFoundException;
import com.duoc.msvehiculos.mapper.VehiculoMapper;
import com.duoc.msvehiculos.model.Categoria;
import com.duoc.msvehiculos.model.Vehiculo;
import com.duoc.msvehiculos.repository.CategoriaRepository;
import com.duoc.msvehiculos.repository.VehiculoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class VehiculoService {

    private final VehiculoRepository vehiculoRepository;
    private final CategoriaRepository categoriaRepository;

    public List<VehiculoDTO> findAll() {
        log.info("Listando todos los vehiculos");

        try {
            return vehiculoRepository.findAll()
                    .stream()
                    .map(VehiculoMapper::toDTO)
                    .toList();
        } catch (Exception e) {
            log.error("Error al listar vehiculos", e);
            throw e;
        }
    }

    public VehiculoDTO findById(Integer id) {
        log.info("Buscando vehiculo con id: {}", id);

        try {
            Vehiculo vehiculo = vehiculoRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Vehiculo no encontrado con id: " + id));

            return VehiculoMapper.toDTO(vehiculo);
        } catch (Exception e) {
            log.error("Error al buscar vehiculo con id: {}", id, e);
            throw e;
        }
    }

    public VehiculoDTO save(VehiculoRequestDTO dto) {
        log.info("Guardando nuevo vehiculo con patente: {}", dto.getPatente());

        try {
            Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada con id: " + dto.getCategoriaId()));

            Vehiculo vehiculo = VehiculoMapper.toEntity(dto, categoria);
            Vehiculo vehiculoGuardado = vehiculoRepository.save(vehiculo);

            return VehiculoMapper.toDTO(vehiculoGuardado);
        } catch (Exception e) {
            log.error("Error al guardar vehiculo", e);
            throw e;
        }
    }

    public VehiculoDTO update(Integer id, VehiculoRequestDTO dto) {
        log.info("Actualizando vehiculo con id: {}", id);

        try {
            Vehiculo vehiculo = vehiculoRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Vehiculo no encontrado con id: " + id));

            Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada con id: " + dto.getCategoriaId()));

            // agregado: actualiza campo por campo para cumplir la nota PUT
            VehiculoMapper.updateEntity(vehiculo, dto, categoria);

            Vehiculo vehiculoActualizado = vehiculoRepository.save(vehiculo);

            return VehiculoMapper.toDTO(vehiculoActualizado);
        } catch (Exception e) {
            log.error("Error al actualizar vehiculo con id: {}", id, e);
            throw e;
        }
    }

    public void delete(Integer id) {
        log.info("Eliminando vehiculo con id: {}", id);

        try {
            Vehiculo vehiculo = vehiculoRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Vehiculo no encontrado con id: " + id));

            vehiculoRepository.delete(vehiculo);
        } catch (Exception e) {
            log.error("Error al eliminar vehiculo con id: {}", id, e);
            throw e;
        }
    }

    // agregado: metodo para la query requerida por la prueba
    public List<VehiculoDTO> buscarDisponiblesPorPrecioMenor(BigDecimal precioMaximo) {
        log.info("Buscando vehiculos disponibles con precio menor a: {}", precioMaximo);

        try {
            return vehiculoRepository.findByDisponibleTrueAndPrecioArriendoDiarioLessThan(precioMaximo)
                    .stream()
                    .map(VehiculoMapper::toDTO)
                    .toList();
        } catch (Exception e) {
            log.error("Error al buscar vehiculos disponibles por precio", e);
            throw e;
        }
    }
}
