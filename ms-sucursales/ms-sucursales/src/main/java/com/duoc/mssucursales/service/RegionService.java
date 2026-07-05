package com.duoc.mssucursales.service;

import com.duoc.mssucursales.dto.RegionDTO;
import com.duoc.mssucursales.dto.RegionRequestDTO;
import com.duoc.mssucursales.exception.ResourceNotFoundException;
import com.duoc.mssucursales.mapper.RegionMapper;
import com.duoc.mssucursales.model.Region;
import com.duoc.mssucursales.repository.RegionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegionService {

    private static final Logger log = LoggerFactory.getLogger(RegionService.class);

    private final RegionRepository regionRepository;
    private final RegionMapper regionMapper;

    public RegionService(RegionRepository regionRepository, RegionMapper regionMapper) {
        this.regionRepository = regionRepository;
        this.regionMapper = regionMapper;
    }

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
            log.info("Buscando región con id: {}", id);

            Region region = regionRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Región no encontrada con id: " + id));

            return regionMapper.toDTO(region);

        } catch (ResourceNotFoundException e) {
            log.error("Región no encontrada con id: {}", id);
            throw e;
        } catch (Exception e) {
            log.error("Error al buscar región con id: {}", id, e);
            throw e;
        }
    }

    public RegionDTO save(RegionRequestDTO requestDTO) {
        try {
            log.info("Guardando nueva región");

            Region region = regionMapper.toEntity(requestDTO);
            Region regionGuardada = regionRepository.save(region);

            return regionMapper.toDTO(regionGuardada);

        } catch (Exception e) {
            log.error("Error al guardar región", e);
            throw e;
        }
    }

    public RegionDTO update(Integer id, RegionRequestDTO requestDTO) {
        try {
            log.info("Actualizando región con id: {}", id);

            Region region = regionRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Región no encontrada con id: " + id));

            regionMapper.updateEntity(region, requestDTO);

            Region regionActualizada = regionRepository.save(region);

            return regionMapper.toDTO(regionActualizada);

        } catch (ResourceNotFoundException e) {
            log.error("Región no encontrada con id: {}", id);
            throw e;
        } catch (Exception e) {
            log.error("Error al actualizar región con id: {}", id, e);
            throw e;
        }
    }

    public void delete(Integer id) {
        try {
            log.info("Eliminando región con id: {}", id);

            Region region = regionRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Región no encontrada con id: " + id));

            regionRepository.delete(region);

        } catch (ResourceNotFoundException e) {
            log.error("Región no encontrada con id: {}", id);
            throw e;
        } catch (Exception e) {
            log.error("Error al eliminar región con id: {}", id, e);
            throw e;
        }
    }

}
