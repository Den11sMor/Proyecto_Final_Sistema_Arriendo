package com.duoc.mssucursales.service;

import com.duoc.mssucursales.dto.SucursalDTO;
import com.duoc.mssucursales.dto.SucursalRequestDTO;
import com.duoc.mssucursales.exception.ResourceNotFoundException;
import com.duoc.mssucursales.mapper.SucursalMapper;
import com.duoc.mssucursales.model.Region;
import com.duoc.mssucursales.model.Sucursal;
import com.duoc.mssucursales.repository.RegionRepository;
import com.duoc.mssucursales.repository.SucursalRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SucursalServiceTest {

    @Mock
    private SucursalRepository sucursalRepository;

    @Mock
    private RegionRepository regionRepository;

    @Mock
    private SucursalMapper sucursalMapper;

    @InjectMocks
    private SucursalService sucursalService;

    @Test
    void save_deberiaGuardarSucursalConRegion() {
        SucursalRequestDTO request = new SucursalRequestDTO();
        request.setRegionId(1);

        Region region = new Region();
        Sucursal sucursal = new Sucursal();
        SucursalDTO esperado = new SucursalDTO();

        when(regionRepository.findById(1)).thenReturn(Optional.of(region));
        when(sucursalMapper.toEntity(request, region)).thenReturn(sucursal);
        when(sucursalRepository.save(sucursal)).thenReturn(sucursal);
        when(sucursalMapper.toDTO(sucursal)).thenReturn(esperado);

        SucursalDTO resultado = sucursalService.save(request);

        assertSame(esperado, resultado);
        verify(regionRepository).findById(1);
        verify(sucursalRepository).save(sucursal);
    }

    @Test
    void save_deberiaFallarCuandoRegionNoExiste() {
        SucursalRequestDTO request = new SucursalRequestDTO();
        request.setRegionId(99);

        when(regionRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> sucursalService.save(request));

        verifyNoInteractions(sucursalRepository, sucursalMapper);
    }
}