package com.duoc.mssucursales.service;

import com.duoc.mssucursales.dto.SucursalDTO;
import com.duoc.mssucursales.dto.SucursalRequestDTO;
import com.duoc.mssucursales.exception.ResourceNotFoundException;
import com.duoc.mssucursales.mapper.SucursalMapper;
import com.duoc.mssucursales.model.Region;
import com.duoc.mssucursales.model.Sucursal;
import com.duoc.mssucursales.repository.RegionRepository;
import com.duoc.mssucursales.repository.SucursalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SucursalService {

    private static final Logger log = LoggerFactory.getLogger(SucursalService.class);

    private final SucursalRepository sucursalRepository;
    private final RegionRepository regionRepository;
    private final SucursalMapper sucursalMapper;

    public SucursalService(SucursalRepository sucursalRepository,
                           RegionRepository regionRepository,
                           SucursalMapper sucursalMapper) {
        this.sucursalRepository = sucursalRepository;
        this.regionRepository = regionRepository;
        this.sucursalMapper = sucursalMapper;
    }

    public List<SucursalDTO> findAll() {
        try {
            log.info("Listando todas las sucursales");

            return sucursalRepository.findAll()
                    .stream()
                    .map(sucursalMapper::toDTO)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.error("Error al listar sucursales", e);
            throw e;
        }
    }

    public SucursalDTO findById(Integer id) {
        try {
            log.info("Buscando sucursal con id: {}", id);

            Sucursal sucursal = sucursalRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Sucursal no encontrada con id: " + id));

            return sucursalMapper.toDTO(sucursal);

        } catch (ResourceNotFoundException e) {
            log.error("Sucursal no encontrada con id: {}", id);
            throw e;
        } catch (Exception e) {
            log.error("Error al buscar sucursal con id: {}", id, e);
            throw e;
        }
    }

    public SucursalDTO save(SucursalRequestDTO requestDTO) {
        try {
            log.info("Guardando nueva sucursal");

            Region region = regionRepository.findById(requestDTO.getRegionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Región no encontrada con id: " + requestDTO.getRegionId()));

            Sucursal sucursal = sucursalMapper.toEntity(requestDTO, region);
            Sucursal sucursalGuardada = sucursalRepository.save(sucursal);

            return sucursalMapper.toDTO(sucursalGuardada);

        } catch (ResourceNotFoundException e) {
            log.error("Región no encontrada al guardar sucursal");
            throw e;
        } catch (Exception e) {
            log.error("Error al guardar sucursal", e);
            throw e;
        }
    }

    public SucursalDTO update(Integer id, SucursalRequestDTO requestDTO) {
        try {
            log.info("Actualizando sucursal con id: {}", id);

            Sucursal sucursal = sucursalRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Sucursal no encontrada con id: " + id));

            Region region = regionRepository.findById(requestDTO.getRegionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Región no encontrada con id: " + requestDTO.getRegionId()));

            sucursalMapper.updateEntity(sucursal, requestDTO, region);

            Sucursal sucursalActualizada = sucursalRepository.save(sucursal);

            return sucursalMapper.toDTO(sucursalActualizada);

        } catch (ResourceNotFoundException e) {
            log.error("Error de recurso no encontrado al actualizar sucursal");
            throw e;
        } catch (Exception e) {
            log.error("Error al actualizar sucursal con id: {}", id, e);
            throw e;
        }
    }

    public void delete(Integer id) {
        try {
            log.info("Eliminando sucursal con id: {}", id);

            Sucursal sucursal = sucursalRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Sucursal no encontrada con id: " + id));

            sucursalRepository.delete(sucursal);

        } catch (ResourceNotFoundException e) {
            log.error("Sucursal no encontrada con id: {}", id);
            throw e;
        } catch (Exception e) {
            log.error("Error al eliminar sucursal con id: {}", id, e);
            throw e;
        }
    }

    public List<SucursalDTO> listarOperativasOrdenadas() {
        try {
            log.info("Listando sucursales operativas ordenadas por nombre");

            return sucursalRepository.listarSucursalesOperativasOrdenadas()
                    .stream()
                    .map(sucursalMapper::toDTO)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.error("Error al listar sucursales operativas", e);
            throw e;
        }
    }
}


