package com.duoc.ms_clientes.controller;

import com.duoc.ms_clientes.dto.DireccionDTO;
import com.duoc.ms_clientes.dto.DireccionRequestDTO;
import com.duoc.ms_clientes.exception.GlobalExceptionHandler;
import com.duoc.ms_clientes.service.DireccionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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

@WebMvcTest(DireccionController.class)
@Import(GlobalExceptionHandler.class)
class DireccionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DireccionService direccionService;

    private DireccionDTO direccionDTO;
    private DireccionRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        direccionDTO = new DireccionDTO(1, "Av Siempre Viva", 742, "Santiago",
                "Santiago", "Casa azul", true, LocalDate.now(), 1);

        requestDTO = new DireccionRequestDTO("Av Siempre Viva", 742, "Santiago",
                "Santiago", "Casa azul", true, LocalDate.now(), 1);
    }

    @Test
    void testFindAll() throws Exception {
        when(direccionService.findAll()).thenReturn(List.of(direccionDTO));

        mockMvc.perform(get("/api/v1/direcciones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].calle").value("Av Siempre Viva"));

        verify(direccionService, times(1)).findAll();
    }

    @Test
    void testFindById() throws Exception {
        when(direccionService.findById(1)).thenReturn(direccionDTO);

        mockMvc.perform(get("/api/v1/direcciones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.calle").value("Av Siempre Viva"));

        verify(direccionService, times(1)).findById(1);
    }

    @Test
    void testSave() throws Exception {
        when(direccionService.save(any(DireccionRequestDTO.class))).thenReturn(direccionDTO);

        mockMvc.perform(post("/api/v1/direcciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.calle").value("Av Siempre Viva"));

        verify(direccionService, times(1)).save(any(DireccionRequestDTO.class));
    }

    @Test
    void testUpdate() throws Exception {
        when(direccionService.update(eq(1), any(DireccionRequestDTO.class))).thenReturn(direccionDTO);

        mockMvc.perform(put("/api/v1/direcciones/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.calle").value("Av Siempre Viva"));

        verify(direccionService, times(1)).update(eq(1), any(DireccionRequestDTO.class));
    }

    @Test
    void testDelete() throws Exception {
        doNothing().when(direccionService).delete(1);

        mockMvc.perform(delete("/api/v1/direcciones/1"))
                .andExpect(status().isNoContent());

        verify(direccionService, times(1)).delete(1);
    }
}
