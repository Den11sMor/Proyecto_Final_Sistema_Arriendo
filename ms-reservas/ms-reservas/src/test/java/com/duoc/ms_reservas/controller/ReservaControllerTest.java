package com.duoc.ms_reservas.controller;

import com.duoc.ms_reservas.dto.ReservaDTO;
import com.duoc.ms_reservas.dto.ReservaRequestDTO;
import com.duoc.ms_reservas.exception.GlobalExceptionHandler;
import com.duoc.ms_reservas.service.ReservaService;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservaController.class)
@Import(GlobalExceptionHandler.class)
class ReservaControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private ReservaService reservaService;

    private ReservaDTO reservaDTO;
    private ReservaRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        reservaDTO = new ReservaDTO(1, 1, 2, LocalDate.now(), LocalDate.now().plusDays(3), 3,
                new BigDecimal("90000"), "Viaje de prueba", true, 1, "Pendiente");
        requestDTO = new ReservaRequestDTO(1, 2, LocalDate.now(), LocalDate.now().plusDays(3), 3,
                new BigDecimal("90000"), "Viaje de prueba", true, 1);
    }

    @Test
    void findAll_ReturnsOk() throws Exception {
        when(reservaService.findAll()).thenReturn(List.of(reservaDTO));
        mockMvc.perform(get("/api/v1/reservas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombreEstadoReserva").value("Pendiente"));
        verify(reservaService).findAll();
    }

    @Test
    void findById_ReturnsOk() throws Exception {
        when(reservaService.findById(1)).thenReturn(reservaDTO);
        mockMvc.perform(get("/api/v1/reservas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
        verify(reservaService).findById(1);
    }

    @Test
    void save_ReturnsCreated() throws Exception {
        when(reservaService.save(any(ReservaRequestDTO.class))).thenReturn(reservaDTO);
        mockMvc.perform(post("/api/v1/reservas").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.clienteId").value(1));
        verify(reservaService).save(any(ReservaRequestDTO.class));
    }

    @Test
    void update_ReturnsOk() throws Exception {
        when(reservaService.update(eq(1), any(ReservaRequestDTO.class))).thenReturn(reservaDTO);
        mockMvc.perform(put("/api/v1/reservas/1").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
        verify(reservaService).update(eq(1), any(ReservaRequestDTO.class));
    }

    @Test
    void delete_ReturnsNoContent() throws Exception {
        doNothing().when(reservaService).delete(1);
        mockMvc.perform(delete("/api/v1/reservas/1"))
                .andExpect(status().isNoContent());
        verify(reservaService).delete(1);
    }

    @Test
    void findByFechaInicioDesde_ReturnsOk() throws Exception {
        when(reservaService.findByFechaInicioDesde(LocalDate.of(2026, 7, 1))).thenReturn(List.of(reservaDTO));
        mockMvc.perform(get("/api/v1/reservas/desde-fecha").param("fecha", "2026-07-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
        verify(reservaService).findByFechaInicioDesde(LocalDate.of(2026, 7, 1));
    }
}
