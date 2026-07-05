package com.duoc.msvehiculos.controller;

import com.duoc.msvehiculos.dto.CategoriaDTO;
import com.duoc.msvehiculos.dto.CategoriaRequestDTO;
import com.duoc.msvehiculos.exception.GlobalExceptionHandler;
import com.duoc.msvehiculos.service.CategoriaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoriaController.class)
@Import(GlobalExceptionHandler.class)
class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CategoriaService categoriaService;

    private CategoriaDTO categoriaDTO;
    private CategoriaRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        categoriaDTO = CategoriaDTO.builder()
                .id(1)
                .nombre("Sedan")
                .descripcion("Vehiculos compactos")
                .tarifaBase(new BigDecimal("25000"))
                .capacidadPasajeros(5)
                .activa(true)
                .fechaCreacion(LocalDate.now())
                .build();

        requestDTO = CategoriaRequestDTO.builder()
                .nombre("Sedan")
                .descripcion("Vehiculos compactos")
                .tarifaBase(new BigDecimal("25000"))
                .capacidadPasajeros(5)
                .activa(true)
                .fechaCreacion(LocalDate.now())
                .build();
    }

    @Test
    void testFindAll() throws Exception {
        when(categoriaService.findAll()).thenReturn(List.of(categoriaDTO));

        mockMvc.perform(get("/api/v1/categorias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Sedan"));

        verify(categoriaService, times(1)).findAll();
    }

    @Test
    void testFindById() throws Exception {
        when(categoriaService.findById(1)).thenReturn(categoriaDTO);

        mockMvc.perform(get("/api/v1/categorias/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Sedan"));

        verify(categoriaService, times(1)).findById(1);
    }

    @Test
    void testSave() throws Exception {
        when(categoriaService.save(any(CategoriaRequestDTO.class))).thenReturn(categoriaDTO);

        mockMvc.perform(post("/api/v1/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Sedan"));

        verify(categoriaService, times(1)).save(any(CategoriaRequestDTO.class));
    }

    @Test
    void testUpdate() throws Exception {
        when(categoriaService.update(eq(1), any(CategoriaRequestDTO.class))).thenReturn(categoriaDTO);

        mockMvc.perform(put("/api/v1/categorias/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Sedan"));

        verify(categoriaService, times(1)).update(eq(1), any(CategoriaRequestDTO.class));
    }

    @Test
    void testDelete() throws Exception {
        doNothing().when(categoriaService).delete(1);

        mockMvc.perform(delete("/api/v1/categorias/1"))
                .andExpect(status().isNoContent());

        verify(categoriaService, times(1)).delete(1);
    }
}
