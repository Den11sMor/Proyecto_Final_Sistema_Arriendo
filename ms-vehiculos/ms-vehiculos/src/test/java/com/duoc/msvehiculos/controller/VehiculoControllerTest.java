package com.duoc.msvehiculos.controller;

import com.duoc.msvehiculos.dto.VehiculoDTO;
import com.duoc.msvehiculos.dto.VehiculoRequestDTO;
import com.duoc.msvehiculos.exception.GlobalExceptionHandler;
import com.duoc.msvehiculos.service.VehiculoService;
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

@WebMvcTest(VehiculoController.class)
@Import(GlobalExceptionHandler.class)
class VehiculoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VehiculoService vehiculoService;

    private VehiculoDTO vehiculoDTO;
    private VehiculoRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        vehiculoDTO = VehiculoDTO.builder()
                .id(1)
                .patente("ABCD12")
                .marca("Toyota")
                .modelo("Corolla")
                .anio(2022)
                .color("Blanco")
                .precioArriendoDiario(new BigDecimal("35000"))
                .kilometraje(25000)
                .disponible(true)
                .fechaIngreso(LocalDate.now())
                .categoriaId(1)
                .categoriaNombre("Sedan")
                .build();

        requestDTO = VehiculoRequestDTO.builder()
                .patente("ABCD12")
                .marca("Toyota")
                .modelo("Corolla")
                .anio(2022)
                .color("Blanco")
                .precioArriendoDiario(new BigDecimal("35000"))
                .kilometraje(25000)
                .disponible(true)
                .fechaIngreso(LocalDate.now())
                .categoriaId(1)
                .build();
    }

    @Test
    void testFindAll() throws Exception {
        when(vehiculoService.findAll()).thenReturn(List.of(vehiculoDTO));

        mockMvc.perform(get("/api/v1/vehiculos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].patente").value("ABCD12"));

        verify(vehiculoService, times(1)).findAll();
    }

    @Test
    void testFindById() throws Exception {
        when(vehiculoService.findById(1)).thenReturn(vehiculoDTO);

        mockMvc.perform(get("/api/v1/vehiculos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.patente").value("ABCD12"));

        verify(vehiculoService, times(1)).findById(1);
    }

    @Test
    void testSave() throws Exception {
        when(vehiculoService.save(any(VehiculoRequestDTO.class))).thenReturn(vehiculoDTO);

        mockMvc.perform(post("/api/v1/vehiculos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.patente").value("ABCD12"));

        verify(vehiculoService, times(1)).save(any(VehiculoRequestDTO.class));
    }

    @Test
    void testUpdate() throws Exception {
        when(vehiculoService.update(eq(1), any(VehiculoRequestDTO.class))).thenReturn(vehiculoDTO);

        mockMvc.perform(put("/api/v1/vehiculos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.patente").value("ABCD12"));

        verify(vehiculoService, times(1)).update(eq(1), any(VehiculoRequestDTO.class));
    }

    @Test
    void testDelete() throws Exception {
        doNothing().when(vehiculoService).delete(1);

        mockMvc.perform(delete("/api/v1/vehiculos/1"))
                .andExpect(status().isNoContent());

        verify(vehiculoService, times(1)).delete(1);
    }

    @Test
    void testBuscarDisponiblesPorPrecioMenor() throws Exception {
        BigDecimal precioMaximo = new BigDecimal("50000");
        when(vehiculoService.buscarDisponiblesPorPrecioMenor(precioMaximo)).thenReturn(List.of(vehiculoDTO));

        mockMvc.perform(get("/api/v1/vehiculos/disponibles/precio-menor/50000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].patente").value("ABCD12"));

        verify(vehiculoService, times(1)).buscarDisponiblesPorPrecioMenor(precioMaximo);
    }
}
