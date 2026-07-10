package com.duoc.mssucursales.controller;

import com.duoc.mssucursales.dto.SucursalDTO;
import com.duoc.mssucursales.dto.SucursalRequestDTO;
import com.duoc.mssucursales.exception.GlobalExceptionHandler;
import com.duoc.mssucursales.service.SucursalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
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

@WebMvcTest(controllers = {SucursalController.class, GlobalExceptionHandler.class})
@ActiveProfiles("test")
class SucursalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private SucursalService sucursalService;

    @Test
    @DisplayName("Debe listar sucursales")
    void testFindAll() throws Exception {
        when(sucursalService.findAll()).thenReturn(List.of(crearSucursalDTO()));

        mockMvc.perform(get("/api/v1/sucursales"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Sucursal Santiago Centro"));
    }

    @Test
    @DisplayName("Debe buscar sucursal por id")
    void testFindById() throws Exception {
        when(sucursalService.findById(1)).thenReturn(crearSucursalDTO());

        mockMvc.perform(get("/api/v1/sucursales/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.regionId").value(1));
    }

    @Test
    @DisplayName("Debe crear sucursal")
    void testSave() throws Exception {
        when(sucursalService.save(any(SucursalRequestDTO.class))).thenReturn(crearSucursalDTO());

        mockMvc.perform(post("/api/v1/sucursales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(crearRequest())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("Debe actualizar sucursal")
    void testUpdate() throws Exception {
        when(sucursalService.update(eq(1), any(SucursalRequestDTO.class))).thenReturn(crearSucursalDTO());

        mockMvc.perform(put("/api/v1/sucursales/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(crearRequest())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("Debe eliminar sucursal")
    void testDelete() throws Exception {
        doNothing().when(sucursalService).delete(1);

        mockMvc.perform(delete("/api/v1/sucursales/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Debe listar sucursales operativas")
    void testListarOperativasOrdenadas() throws Exception {
        when(sucursalService.listarOperativasOrdenadas()).thenReturn(List.of(crearSucursalDTO()));

        mockMvc.perform(get("/api/v1/sucursales/operativas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].operativa").value(true));
    }

    private SucursalDTO crearSucursalDTO() {
        return new SucursalDTO(
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
    }

    private SucursalRequestDTO crearRequest() {
        return new SucursalRequestDTO(
                "Sucursal Santiago Centro",
                "Avenida Libertador Bernardo O'Higgins 123",
                "Santiago",
                223456789,
                true,
                LocalDate.of(2024, 3, 15),
                1
        );
    }
}
