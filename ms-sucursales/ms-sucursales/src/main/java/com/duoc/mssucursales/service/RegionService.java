package com.duoc.mssucursales.service;

import com.duoc.mssucursales.dto.RegionDTO;
import com.duoc.mssucursales.dto.RegionRequestDTO;
import com.duoc.mssucursales.exception.ResourceNotFoundException;
import com.duoc.mssucursales.mapper.RegionMapper;
import com.duoc.mssucursales.model.Region;
import com.duoc.mssucursales.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Contiene la logica de negocio para regiones
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class RegionService {

    private final RegionRepository regionRepository;
    private final RegionMapper regionMapper;

    public List<RegionDTO> findAll() {
        try {
            log.info("Listando todas las regiones");

            return regionRepository.findAll()
                    .stream()
                    .map(regionMapper::toDTO)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.error("Error al listar regiones", e);
            throw e;
        }
    }

    public RegionDTO findById(Integer id) {
        try {
            log.info("Buscando region con id: {}", id);

            Region region = regionRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Region no encontrada con id: " + id));

            return regionMapper.toDTO(region);

        } catch (ResourceNotFoundException e) {
            log.error("Region no encontrada con id: {}", id);
            throw e;
        } catch (Exception e) {
            log.error("Error al buscar region con id: {}", id, e);
            throw e;
        }
    }

    public RegionDTO save(RegionRequestDTO requestDTO) {
        try {
            log.info("Guardando nueva region");

            Region region = regionMapper.toEntity(requestDTO);
            Region regionGuardada = regionRepository.save(region);

            return regionMapper.toDTO(regionGuardada);

        } catch (Exception e) {
            log.error("Error al guardar region", e);
            throw e;
        }
    }

    public RegionDTO update(Integer id, RegionRequestDTO requestDTO) {
        try {
            log.info("Actualizando region con id: {}", id);

            Region region = regionRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Region no encontrada con id: " + id));

            regionMapper.updateEntity(region, requestDTO);

            Region regionActualizada = regionRepository.save(region);

            return regionMapper.toDTO(regionActualizada);

        } catch (ResourceNotFoundException e) {
            log.error("Region no encontrada con id: {}", id);
            throw e;
        } catch (Exception e) {
            log.error("Error al actualizar region con id: {}", id, e);
            throw e;
        }
    }

    public void delete(Integer id) {
        try {
            log.info("Eliminando region con id: {}", id);

            Region region = regionRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Region no encontrada con id: " + id));

            regionRepository.delete(region);

        } catch (ResourceNotFoundException e) {
            log.error("Region no encontrada con id: {}", id);
            throw e;
        } catch (Exception e) {
            log.error("Error al eliminar region con id: {}", id, e);
            throw e;
        }
    }
}