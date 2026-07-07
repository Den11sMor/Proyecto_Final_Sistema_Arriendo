package com.duoc.mssucursales.service;

import com.duoc.mssucursales.dto.RegionDTO;
import com.duoc.mssucursales.dto.RegionRequestDTO;
import com.duoc.mssucursales.exception.ResourceNotFoundException;
import com.duoc.mssucursales.mapper.RegionMapper;
import com.duoc.mssucursales.model.Region;
import com.duoc.mssucursales.repository.RegionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
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

    private Region region;
    private RegionDTO regionDTO;
    private RegionRequestDTO request;

    @BeforeEach
    void setUp() {
        region = new Region(
                1,
                "Region Metropolitana",
                "RM",
                13,
                "Santiago",
                true,
                LocalDate.of(2024, 1, 10),
                List.of()
        );

        regionDTO = new RegionDTO(
                1,
                "Region Metropolitana",
                "RM",
                13,
                "Santiago",
                true,
                LocalDate.of(2024, 1, 10)
        );

        request = new RegionRequestDTO();
        request.setNombre("Region Metropolitana");
        request.setCodigo("RM");
        request.setNumeroRegion(13);
        request.setCapitalRegional("Santiago");
        request.setActiva(true);
        request.setFechaCreacion(LocalDate.of(2024, 1, 10));
    }

    @Test
    @DisplayName("Debe listar todas las regiones")
    void testFindAll() {
        when(regionRepository.findAll()).thenReturn(List.of(region));
        when(regionMapper.toDTO(region)).thenReturn(regionDTO);

        List<RegionDTO> resultado = regionService.findAll();

        assertSame(regionDTO, resultado.get(0));
        verify(regionRepository).findAll();
    }

    @Test
    @DisplayName("Debe buscar region por id")
    void testFindById() {
        when(regionRepository.findById(1)).thenReturn(Optional.of(region));
        when(regionMapper.toDTO(region)).thenReturn(regionDTO);

        RegionDTO resultado = regionService.findById(1);

        assertSame(regionDTO, resultado);
        verify(regionRepository).findById(1);
    }

    @Test
    @DisplayName("Debe fallar cuando la region no existe")
    void testFindByIdNoEncontrado() {
        when(regionRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> regionService.findById(99));

        verify(regionRepository).findById(99);
        verifyNoInteractions(regionMapper);
    }

    @Test
    @DisplayName("Debe guardar region")
    void testSave() {
        when(regionMapper.toEntity(request)).thenReturn(region);
        when(regionRepository.save(region)).thenReturn(region);
        when(regionMapper.toDTO(region)).thenReturn(regionDTO);

        RegionDTO resultado = regionService.save(request);

        assertSame(regionDTO, resultado);
        verify(regionMapper).toEntity(request);
        verify(regionRepository).save(region);
    }

    @Test
    @DisplayName("Debe actualizar region")
    void testUpdate() {
        when(regionRepository.findById(1)).thenReturn(Optional.of(region));
        when(regionRepository.save(region)).thenReturn(region);
        when(regionMapper.toDTO(region)).thenReturn(regionDTO);

        RegionDTO resultado = regionService.update(1, request);

        assertSame(regionDTO, resultado);
        verify(regionMapper).updateEntity(region, request);
        verify(regionRepository).save(region);
    }

    @Test
    @DisplayName("Debe fallar al actualizar una region inexistente")
    void testUpdateNoEncontrado() {
        when(regionRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> regionService.update(99, request));

        verify(regionRepository).findById(99);
    }

    @Test
    @DisplayName("Debe eliminar region")
    void testDelete() {
        when(regionRepository.findById(1)).thenReturn(Optional.of(region));

        regionService.delete(1);

        verify(regionRepository).delete(region);
    }

    @Test
    @DisplayName("Debe fallar al eliminar una region inexistente")
    void testDeleteNoEncontrado() {
        when(regionRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> regionService.delete(99));

        verify(regionRepository).findById(99);
    }
}