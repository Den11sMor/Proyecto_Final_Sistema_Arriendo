package com.duoc.ms_reservas.controller;

import com.duoc.ms_reservas.dto.EstadoReservaDTO;
import com.duoc.ms_reservas.dto.EstadoReservaRequestDTO;
import com.duoc.ms_reservas.exception.GlobalExceptionHandler;
import com.duoc.ms_reservas.service.EstadoReservaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EstadoReservaController.class)
@Import(GlobalExceptionHandler.class)
class EstadoReservaControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private EstadoReservaService estadoReservaService;

    private EstadoReservaDTO estadoDTO;
    private EstadoReservaRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        estadoDTO = new EstadoReservaDTO(1, "Pendiente", "En espera de confirmación", 1, true, LocalDateTime.now());
        requestDTO = new EstadoReservaRequestDTO("Pendiente", "En espera de confirmación", 1, true, LocalDateTime.now());
    }

    @Test
    void findAll_ReturnsOk() throws Exception {
        when(estadoReservaService.findAll()).thenReturn(List.of(estadoDTO));
        mockMvc.perform(get("/api/v1/estados-reserva"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Pendiente"));
        verify(estadoReservaService).findAll();
    }

    @Test
    void findById_ReturnsOk() throws Exception {
        when(estadoReservaService.findById(1)).thenReturn(estadoDTO);
        mockMvc.perform(get("/api/v1/estados-reserva/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
        verify(estadoReservaService).findById(1);
    }

    @Test
    void save_ReturnsCreated() throws Exception {
        when(estadoReservaService.save(any(EstadoReservaRequestDTO.class))).thenReturn(estadoDTO);
        mockMvc.perform(post("/api/v1/estados-reserva").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Pendiente"));
        verify(estadoReservaService).save(any(EstadoReservaRequestDTO.class));
    }

    @Test
    void update_ReturnsOk() throws Exception {
        when(estadoReservaService.update(eq(1), any(EstadoReservaRequestDTO.class))).thenReturn(estadoDTO);
        mockMvc.perform(put("/api/v1/estados-reserva/1").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.prioridad").value(1));
        verify(estadoReservaService).update(eq(1), any(EstadoReservaRequestDTO.class));
    }

    @Test
    void delete_ReturnsNoContent() throws Exception {
        doNothing().when(estadoReservaService).delete(1);
        mockMvc.perform(delete("/api/v1/estados-reserva/1"))
                .andExpect(status().isNoContent());
        verify(estadoReservaService).delete(1);
    }
}
