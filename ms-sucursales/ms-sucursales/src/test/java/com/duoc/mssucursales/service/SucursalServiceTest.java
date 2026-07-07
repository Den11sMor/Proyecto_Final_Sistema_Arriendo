package com.duoc.mssucursales.service;

import com.duoc.mssucursales.dto.SucursalDTO;
import com.duoc.mssucursales.dto.SucursalRequestDTO;
import com.duoc.mssucursales.exception.ResourceNotFoundException;
import com.duoc.mssucursales.mapper.SucursalMapper;
import com.duoc.mssucursales.model.Region;
import com.duoc.mssucursales.model.Sucursal;
import com.duoc.mssucursales.repository.RegionRepository;
import com.duoc.mssucursales.repository.SucursalRepository;
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
class SucursalServiceTest {

    @Mock
    private SucursalRepository sucursalRepository;

    @Mock
    private RegionRepository regionRepository;

    @Mock
    private SucursalMapper sucursalMapper;

    @InjectMocks
    private SucursalService sucursalService;

    private Region region;
    private Sucursal sucursal;
    private SucursalDTO sucursalDTO;
    private SucursalRequestDTO request;

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

        sucursal = new Sucursal(
                1,
                "Sucursal Santiago Centro",
                "Avenida Libertador Bernardo O'Higgins 123",
                "Santiago",
                223456789,
                true,
                LocalDate.of(2024, 3, 15),
                region
        );

        sucursalDTO = new SucursalDTO(
                1,
                "Sucursal Santiago Centro",
                "Avenida Libertador Bernardo O'Higgins 123",
                "Santiago",
                223456789,
                true,
                LocalDate.of(2024, 3, 15),
                1,
                "Region Metropolitana"
        );

        request = new SucursalRequestDTO(
                "Sucursal Santiago Centro",
                "Avenida Libertador Bernardo O'Higgins 123",
                "Santiago",
                223456789,
                true,
                LocalDate.of(2024, 3, 15),
                1
        );
    }

    @Test
    @DisplayName("Debe listar todas las sucursales")
    void testFindAll() {
        when(sucursalRepository.findAll()).thenReturn(List.of(sucursal));
        when(sucursalMapper.toDTO(sucursal)).thenReturn(sucursalDTO);

        List<SucursalDTO> resultado = sucursalService.findAll();

        assertSame(sucursalDTO, resultado.get(0));
        verify(sucursalRepository).findAll();
    }

    @Test
    @DisplayName("Debe buscar sucursal por id")
    void testFindById() {
        when(sucursalRepository.findById(1)).thenReturn(Optional.of(sucursal));
        when(sucursalMapper.toDTO(sucursal)).thenReturn(sucursalDTO);

        SucursalDTO resultado = sucursalService.findById(1);

        assertSame(sucursalDTO, resultado);
        verify(sucursalRepository).findById(1);
    }

    @Test
    @DisplayName("Debe fallar cuando la sucursal no existe")
    void testFindByIdNoEncontrado() {
        when(sucursalRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> sucursalService.findById(99));

        verify(sucursalRepository).findById(99);
        verifyNoInteractions(sucursalMapper);
    }

    @Test
    @DisplayName("Debe guardar sucursal con region")
    void testSave() {
        when(regionRepository.findById(1)).thenReturn(Optional.of(region));
        when(sucursalMapper.toEntity(request, region)).thenReturn(sucursal);
        when(sucursalRepository.save(sucursal)).thenReturn(sucursal);
        when(sucursalMapper.toDTO(sucursal)).thenReturn(sucursalDTO);

        SucursalDTO resultado = sucursalService.save(request);

        assertSame(sucursalDTO, resultado);
        verify(regionRepository).findById(1);
        verify(sucursalRepository).save(sucursal);
    }

    @Test
    @DisplayName("Debe fallar al guardar cuando la region no existe")
    void testSaveRegionNoEncontrada() {
        when(regionRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> sucursalService.save(request));

        verifyNoInteractions(sucursalRepository, sucursalMapper);
    }

    @Test
    @DisplayName("Debe actualizar sucursal")
    void testUpdate() {
        when(sucursalRepository.findById(1)).thenReturn(Optional.of(sucursal));
        when(regionRepository.findById(1)).thenReturn(Optional.of(region));
        when(sucursalRepository.save(sucursal)).thenReturn(sucursal);
        when(sucursalMapper.toDTO(sucursal)).thenReturn(sucursalDTO);

        SucursalDTO resultado = sucursalService.update(1, request);

        assertSame(sucursalDTO, resultado);
        verify(sucursalMapper).updateEntity(sucursal, request, region);
        verify(sucursalRepository).save(sucursal);
    }

    @Test
    @DisplayName("Debe fallar al actualizar una sucursal inexistente")
    void testUpdateSucursalNoEncontrada() {
        when(sucursalRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> sucursalService.update(99, request));

        verify(sucursalRepository).findById(99);
    }

    @Test
    @DisplayName("Debe fallar al actualizar cuando la region no existe")
    void testUpdateRegionNoEncontrada() {
        when(sucursalRepository.findById(1)).thenReturn(Optional.of(sucursal));
        when(regionRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> sucursalService.update(1, request));

        verify(regionRepository).findById(1);
    }

    @Test
    @DisplayName("Debe eliminar sucursal")
    void testDelete() {
        when(sucursalRepository.findById(1)).thenReturn(Optional.of(sucursal));

        sucursalService.delete(1);

        verify(sucursalRepository).delete(sucursal);
    }

    @Test
    @DisplayName("Debe fallar al eliminar una sucursal inexistente")
    void testDeleteNoEncontrado() {
        when(sucursalRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> sucursalService.delete(99));

        verify(sucursalRepository).findById(99);
    }

    @Test
    @DisplayName("Debe listar sucursales operativas ordenadas")
    void testListarOperativasOrdenadas() {
        when(sucursalRepository.listarSucursalesOperativasOrdenadas()).thenReturn(List.of(sucursal));
        when(sucursalMapper.toDTO(sucursal)).thenReturn(sucursalDTO);

        List<SucursalDTO> resultado = sucursalService.listarOperativasOrdenadas();

        assertSame(sucursalDTO, resultado.get(0));
        verify(sucursalRepository).listarSucursalesOperativasOrdenadas();
    }
}