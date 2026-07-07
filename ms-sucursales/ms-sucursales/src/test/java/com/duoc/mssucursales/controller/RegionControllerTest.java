package com.duoc.mssucursales.controller;

import com.duoc.mssucursales.dto.RegionDTO;
import com.duoc.mssucursales.dto.RegionRequestDTO;
import com.duoc.mssucursales.exception.GlobalExceptionHandler;
import com.duoc.mssucursales.service.RegionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {RegionController.class, GlobalExceptionHandler.class})
class RegionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private RegionService regionService;

    @Test
    @DisplayName("Debe listar regiones")
    void testFindAll() throws Exception {
        when(regionService.findAll()).thenReturn(List.of(crearRegionDTO()));

        mockMvc.perform(get("/api/v1/regiones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Region Metropolitana"));
    }

    @Test
    @DisplayName("Debe buscar region por id")
    void testFindById() throws Exception {
        when(regionService.findById(1)).thenReturn(crearRegionDTO());

        mockMvc.perform(get("/api/v1/regiones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.codigo").value("RM"));
    }

    @Test
    @DisplayName("Debe crear region")
    void testSave() throws Exception {
        when(regionService.save(any(RegionRequestDTO.class))).thenReturn(crearRegionDTO());

        mockMvc.perform(post("/api/v1/regiones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(crearRequest())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("Debe actualizar region")
    void testUpdate() throws Exception {
        when(regionService.update(eq(1), any(RegionRequestDTO.class))).thenReturn(crearRegionDTO());

        mockMvc.perform(put("/api/v1/regiones/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(crearRequest())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("Debe eliminar region")
    void testDelete() throws Exception {
        doNothing().when(regionService).delete(1);

        mockMvc.perform(delete("/api/v1/regiones/1"))
                .andExpect(status().isNoContent());
    }

    private RegionDTO crearRegionDTO() {
        return new RegionDTO(1, "Region Metropolitana", "RM", 13, "Santiago", true, LocalDate.of(2024, 1, 10));
    }

    private RegionRequestDTO crearRequest() {
        RegionRequestDTO request = new RegionRequestDTO();
        request.setNombre("Region Metropolitana");
        request.setCodigo("RM");
        request.setNumeroRegion(13);
        request.setCapitalRegional("Santiago");
        request.setActiva(true);
        request.setFechaCreacion(LocalDate.of(2024, 1, 10));
        return request;
    }
}