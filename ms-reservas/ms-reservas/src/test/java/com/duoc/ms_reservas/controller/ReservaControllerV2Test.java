package com.duoc.ms_reservas.controller;

import com.duoc.ms_reservas.assemblers.ReservaModelAssembler;
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
import org.springframework.hateoas.EntityModel;
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
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservaControllerV2.class)
@Import(GlobalExceptionHandler.class)
class ReservaControllerV2Test {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockBean private ReservaService reservaService;
    @MockBean private ReservaModelAssembler reservaModelAssembler;

    private ReservaDTO reservaDTO;
    private ReservaRequestDTO requestDTO;
    private EntityModel<ReservaDTO> reservaModel;

    @BeforeEach
    void setUp() {
        reservaDTO = new ReservaDTO(1, 1, 2, LocalDate.now(), LocalDate.now().plusDays(3), 3,
                new BigDecimal("90000"), "Viaje de prueba", true, 1, "Pendiente");

        requestDTO = new ReservaRequestDTO(1, 2, LocalDate.now(), LocalDate.now().plusDays(3), 3,
                new BigDecimal("90000"), "Viaje de prueba", true, 1);

        reservaModel = EntityModel.of(
                reservaDTO,
                linkTo(methodOn(ReservaControllerV2.class).findById(1)).withSelfRel()
        );
    }

    @Test
    void findAll_ReturnsOkWithLinks() throws Exception {
        when(reservaService.findAll()).thenReturn(List.of(reservaDTO));
        when(reservaModelAssembler.toModel(reservaDTO)).thenReturn(reservaModel);

        mockMvc.perform(get("/api/v2/reservas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.reservaDTOList[0].id").value(1))
                .andExpect(jsonPath("$._links.self.href").exists());

        verify(reservaService).findAll();
    }

    @Test
    void findById_ReturnsOkWithLinks() throws Exception {
        when(reservaService.findById(1)).thenReturn(reservaDTO);
        when(reservaModelAssembler.toModel(reservaDTO)).thenReturn(reservaModel);

        mockMvc.perform(get("/api/v2/reservas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$._links.self.href").exists());

        verify(reservaService).findById(1);
    }

    @Test
    void save_ReturnsCreatedWithLinks() throws Exception {
        when(reservaService.save(any(ReservaRequestDTO.class))).thenReturn(reservaDTO);
        when(reservaModelAssembler.toModel(reservaDTO)).thenReturn(reservaModel);

        mockMvc.perform(post("/api/v2/reservas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$._links.self.href").exists());

        verify(reservaService).save(any(ReservaRequestDTO.class));
    }

    @Test
    void update_ReturnsOkWithLinks() throws Exception {
        when(reservaService.update(eq(1), any(ReservaRequestDTO.class))).thenReturn(reservaDTO);
        when(reservaModelAssembler.toModel(reservaDTO)).thenReturn(reservaModel);

        mockMvc.perform(put("/api/v2/reservas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$._links.self.href").exists());

        verify(reservaService).update(eq(1), any(ReservaRequestDTO.class));
    }

    @Test
    void delete_ReturnsNoContent() throws Exception {
        doNothing().when(reservaService).delete(1);

        mockMvc.perform(delete("/api/v2/reservas/1"))
                .andExpect(status().isNoContent());

        verify(reservaService).delete(1);
    }

    @Test
    void findByFechaInicioDesde_ReturnsOkWithLinks() throws Exception {
        LocalDate fecha = LocalDate.of(2026, 7, 1);

        when(reservaService.findByFechaInicioDesde(fecha)).thenReturn(List.of(reservaDTO));
        when(reservaModelAssembler.toModel(reservaDTO)).thenReturn(reservaModel);

        mockMvc.perform(get("/api/v2/reservas/desde-fecha").param("fecha", "2026-07-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.reservaDTOList[0].id").value(1))
                .andExpect(jsonPath("$._links.self.href").exists());

        verify(reservaService).findByFechaInicioDesde(fecha);
    }
}