package com.duoc.mssucursales.mapper;

import com.duoc.mssucursales.dto.RegionDTO;
import com.duoc.mssucursales.dto.RegionRequestDTO;
import com.duoc.mssucursales.model.Region;
import org.springframework.stereotype.Component;

@Component
public class RegionMapper {

    public RegionDTO toDTO(Region region) {
        if (region == null) {
            return null;
        }

        RegionDTO dto = new RegionDTO();

        dto.setId(region.getId());
        dto.setNombre(region.getNombre());
        dto.setCodigo(region.getCodigo());
        dto.setNumeroRegion(region.getNumeroRegion());
        dto.setCapitalRegional(region.getCapitalRegional());
        dto.setActiva(region.getActiva());
        dto.setFechaCreacion(region.getFechaCreacion());

        return dto;
    }

    public Region toEntity(RegionRequestDTO requestDTO) {
        if (requestDTO == null) {
            return null;
        }

        Region region = new Region();

        region.setNombre(requestDTO.getNombre());
        region.setCodigo(requestDTO.getCodigo());
        region.setNumeroRegion(requestDTO.getNumeroRegion());
        region.setCapitalRegional(requestDTO.getCapitalRegional());
        region.setActiva(requestDTO.getActiva());
        region.setFechaCreacion(requestDTO.getFechaCreacion());

        return region;
    }

    public void updateEntity(Region region, RegionRequestDTO requestDTO) {
        region.setNombre(requestDTO.getNombre());
        region.setCodigo(requestDTO.getCodigo());
        region.setNumeroRegion(requestDTO.getNumeroRegion());
        region.setCapitalRegional(requestDTO.getCapitalRegional());
        region.setActiva(requestDTO.getActiva());
        region.setFechaCreacion(requestDTO.getFechaCreacion());
    }

}
