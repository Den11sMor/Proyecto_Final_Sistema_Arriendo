package com.duoc.ms_reportes.controller;

import com.duoc.ms_reportes.dto.ReporteDTO;
import com.duoc.ms_reportes.dto.ReporteRequestDTO;
import com.duoc.ms_reportes.exception.GlobalExceptionHandler;
import com.duoc.ms_reportes.service.ReporteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
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

@WebMvcTest(controllers = {ReporteController.class, GlobalExceptionHandler.class})
class ReporteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ReporteService reporteService;

    @Test
    @DisplayName("Debe listar reportes")
    void testFindAll() throws Exception {
        when(reporteService.findAll()).thenReturn(List.of(crearReporteDTO()));

        mockMvc.perform(get("/api/v1/reportes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].reservaId").value(10))
                .andExpect(jsonPath("$[0].pagoId").value(5));
    }

    @Test
    @DisplayName("Debe buscar reporte por id")
    void testFindById() throws Exception {
        when(reporteService.findById(1)).thenReturn(crearReporteDTO());

        mockMvc.perform(get("/api/v1/reportes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.tipoReporte").value("RESUMEN_RESERVA"));
    }

    @Test
    @DisplayName("Debe crear reporte")
    void testSave() throws Exception {
        when(reporteService.save(any(ReporteRequestDTO.class))).thenReturn(crearReporteDTO());

        mockMvc.perform(post("/api/v1/reportes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(crearRequest())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.descripcion").value("Reporte generado para reserva confirmada"));
    }

    @Test
    @DisplayName("Debe actualizar reporte")
    void testUpdate() throws Exception {
        when(reporteService.update(eq(1), any(ReporteRequestDTO.class))).thenReturn(crearReporteDTO());

        mockMvc.perform(put("/api/v1/reportes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(crearRequest())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("Debe eliminar reporte")
    void testDelete() throws Exception {
        doNothing().when(reporteService).delete(1);

        mockMvc.perform(delete("/api/v1/reportes/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Debe buscar reportes por reserva")
    void testFindByReservaId() throws Exception {
        when(reporteService.findByReservaId(10)).thenReturn(List.of(crearReporteDTO()));

        mockMvc.perform(get("/api/v1/reportes/reserva/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].reservaId").value(10));
    }

    @Test
    @DisplayName("Debe buscar reportes por pago confirmado")
    void testFindByPagoConfirmado() throws Exception {
        when(reporteService.findByPagoConfirmado(true)).thenReturn(List.of(crearReporteDTO()));

        mockMvc.perform(get("/api/v1/reportes/pago-confirmado")
                        .param("confirmado", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].pagoConfirmado").value(true));
    }

    private ReporteDTO crearReporteDTO() {
        return new ReporteDTO(
                1,
                10,
                5,
                "RESUMEN_RESERVA",
                LocalDate.of(2024, 4, 20),
                "Reporte generado para reserva confirmada",
                new BigDecimal("125000"),
                new BigDecimal("125000"),
                true,
                true
        );
    }

    private ReporteRequestDTO crearRequest() {
        return new ReporteRequestDTO(
                10,
                5,
                "RESUMEN_RESERVA",
                "Reporte generado para reserva confirmada"
        );
    }
}