package com.duoc.ms_pagos.controller;

import com.duoc.ms_pagos.assemblers.PagoModelAssembler;
import com.duoc.ms_pagos.dto.PagoDTO;
import com.duoc.ms_pagos.dto.PagoRequestDTO;
import com.duoc.ms_pagos.exception.GlobalExceptionHandler;
import com.duoc.ms_pagos.service.PagoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Pruebas de rutas V2 para pagos con HATEOAS.
 */
@WebMvcTest(PagoControllerV2.class)
@Import({GlobalExceptionHandler.class, PagoModelAssembler.class})
@ActiveProfiles("test")
@DisplayName("PagoController V2")
class PagoControllerV2Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PagoService pagoService;

    private PagoDTO pagoDTO;
    private PagoRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        pagoDTO = new PagoDTO(
                1,
                10,
                "Tarjeta de credito",
                new BigDecimal("125000"),
                "TX-2024-0001",
                true,
                LocalDate.of(2024, 4, 20),
                "Pago confirmado"
        );

        requestDTO = new PagoRequestDTO(
                10,
                "Tarjeta de credito",
                new BigDecimal("125000"),
                "TX-2024-0001",
                true,
                LocalDate.of(2024, 4, 20),
                "Pago confirmado"
        );
    }

    @Test
    @DisplayName("Debe listar pagos con HATEOAS")
    void testFindAllV2() throws Exception {
        when(pagoService.findAll()).thenReturn(List.of(pagoDTO));

        mockMvc.perform(get("/api/v2/pagos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.self.href").exists());

        verify(pagoService, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe buscar pago por id con HATEOAS")
    void testFindByIdV2() throws Exception {
        when(pagoService.findById(1)).thenReturn(pagoDTO);

        mockMvc.perform(get("/api/v2/pagos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.metodoPago").value("Tarjeta de credito"))
                .andExpect(jsonPath("$._links.self.href").exists());

        verify(pagoService, times(1)).findById(1);
    }

    @Test
    @DisplayName("Debe crear pago con HATEOAS")
    void testSaveV2() throws Exception {
        when(pagoService.save(any(PagoRequestDTO.class))).thenReturn(pagoDTO);

        mockMvc.perform(post("/api/v2/pagos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.metodoPago").value("Tarjeta de credito"))
                .andExpect(jsonPath("$._links.self.href").exists());

        verify(pagoService, times(1)).save(any(PagoRequestDTO.class));
    }

    @Test
    @DisplayName("Debe actualizar pago con HATEOAS")
    void testUpdateV2() throws Exception {
        when(pagoService.update(eq(1), any(PagoRequestDTO.class))).thenReturn(pagoDTO);

        mockMvc.perform(put("/api/v2/pagos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.metodoPago").value("Tarjeta de credito"))
                .andExpect(jsonPath("$._links.self.href").exists());

        verify(pagoService, times(1)).update(eq(1), any(PagoRequestDTO.class));
    }

    @Test
    @DisplayName("Debe eliminar pago")
    void testDeleteV2() throws Exception {
        doNothing().when(pagoService).delete(1);

        mockMvc.perform(delete("/api/v2/pagos/1"))
                .andExpect(status().isNoContent());

        verify(pagoService, times(1)).delete(1);
    }

    @Test
    @DisplayName("Debe buscar pagos por rango de monto con HATEOAS")
    void testBuscarPorRangoMontoV2() throws Exception {
        BigDecimal min = new BigDecimal("10000");
        BigDecimal max = new BigDecimal("200000");

        when(pagoService.buscarPorRangoMonto(min, max)).thenReturn(List.of(pagoDTO));

        mockMvc.perform(get("/api/v2/pagos/rango")
                        .param("min", "10000")
                        .param("max", "200000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.self.href").exists());

        verify(pagoService, times(1)).buscarPorRangoMonto(min, max);
    }
}
