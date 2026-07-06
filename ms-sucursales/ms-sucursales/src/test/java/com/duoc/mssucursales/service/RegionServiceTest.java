package com.duoc.mssucursales.service;

import com.duoc.mssucursales.dto.RegionDTO;
import com.duoc.mssucursales.dto.RegionRequestDTO;
import com.duoc.mssucursales.exception.ResourceNotFoundException;
import com.duoc.mssucursales.mapper.RegionMapper;
import com.duoc.mssucursales.model.Region;
import com.duoc.mssucursales.repository.RegionRepository;
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
class RegionServiceTest {

    @Mock
    private RegionRepository regionRepository;

    @Mock
    private RegionMapper regionMapper;

    @InjectMocks
    private RegionService regionService;

    @Test
    void save_deberiaGuardarYRetornarRegion() {
        RegionRequestDTO request = new RegionRequestDTO();
        Region region = new Region();
        RegionDTO esperado = new RegionDTO();

        when(regionMapper.toEntity(request)).thenReturn(region);
        when(regionRepository.save(region)).thenReturn(region);
        when(regionMapper.toDTO(region)).thenReturn(esperado);

        RegionDTO resultado = regionService.save(request);

        assertSame(esperado, resultado);
        verify(regionRepository).save(region);
    }

    @Test
    void findById_deberiaFallarCuandoRegionNoExiste() {
        when(regionRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> regionService.findById(99));

        verify(regionRepository).findById(99);
        verifyNoInteractions(regionMapper);
    }
}
