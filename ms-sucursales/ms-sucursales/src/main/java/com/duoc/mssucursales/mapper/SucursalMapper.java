package com.duoc.mssucursales.mapper;


import com.duoc.mssucursales.dto.SucursalDTO;
import com.duoc.mssucursales.dto.SucursalRequestDTO;
import com.duoc.mssucursales.model.Region;
import com.duoc.mssucursales.model.Sucursal;
import org.springframework.stereotype.Component;

@Component
public class SucursalMapper {

    public SucursalDTO toDTO(Sucursal sucursal) {
        if (sucursal == null) {
            return null;
        }

        SucursalDTO dto = new SucursalDTO();

        dto.setId(sucursal.getId());
        dto.setNombre(sucursal.getNombre());
        dto.setDireccion(sucursal.getDireccion());
        dto.setComuna(sucursal.getComuna());
        dto.setTelefono(sucursal.getTelefono());
        dto.setOperativa(sucursal.getOperativa());
        dto.setFechaApertura(sucursal.getFechaApertura());

        if (sucursal.getRegion() != null) {
            dto.setRegionId(sucursal.getRegion().getId());
            dto.setNombreRegion(sucursal.getRegion().getNombre());
        }

        return dto;
    }

    public Sucursal toEntity(SucursalRequestDTO requestDTO, Region region) {
        if (requestDTO == null) {
            return null;
        }

        Sucursal sucursal = new Sucursal();

        sucursal.setNombre(requestDTO.getNombre());
        sucursal.setDireccion(requestDTO.getDireccion());
        sucursal.setComuna(requestDTO.getComuna());
        sucursal.setTelefono(requestDTO.getTelefono());
        sucursal.setOperativa(requestDTO.getOperativa());
        sucursal.setFechaApertura(requestDTO.getFechaApertura());
        sucursal.setRegion(region);

        return sucursal;
    }

    public void updateEntity(Sucursal sucursal, SucursalRequestDTO requestDTO, Region region) {
        sucursal.setNombre(requestDTO.getNombre());
        sucursal.setDireccion(requestDTO.getDireccion());
        sucursal.setComuna(requestDTO.getComuna());
        sucursal.setTelefono(requestDTO.getTelefono());
        sucursal.setOperativa(requestDTO.getOperativa());
        sucursal.setFechaApertura(requestDTO.getFechaApertura());
        sucursal.setRegion(region);
    }

}
