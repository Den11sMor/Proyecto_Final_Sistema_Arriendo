package com.duoc.msempleados.controller;

import com.duoc.msempleados.assemblers.EmpleadoModelAssembler;
import com.duoc.msempleados.dto.EmpleadoDTO;
import com.duoc.msempleados.dto.EmpleadoRequestDTO;
import com.duoc.msempleados.exception.GlobalExceptionHandler;
import com.duoc.msempleados.service.EmpleadoService;
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
 * Pruebas de rutas V2 para empleados con HATEOAS.
 */
@WebMvcTest(EmpleadoControllerV2.class)
@Import({GlobalExceptionHandler.class, EmpleadoModelAssembler.class})
@ActiveProfiles("test")
@DisplayName("EmpleadoController V2")
class EmpleadoControllerV2Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmpleadoService empleadoService;

    private EmpleadoDTO empleadoDTO;
    private EmpleadoRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        empleadoDTO = new EmpleadoDTO();
        empleadoDTO.setId(1);
        empleadoDTO.setRut("12345678-9");
        empleadoDTO.setNombre("Juan Perez");
        empleadoDTO.setCargo("Ejecutivo");
        empleadoDTO.setEmail("juan.perez@empresa.cl");
        empleadoDTO.setSueldo(new BigDecimal("850000"));
        empleadoDTO.setActivo(true);
        empleadoDTO.setFechaIngreso(LocalDate.of(2024, 3, 15));

        requestDTO = new EmpleadoRequestDTO();
        requestDTO.setRut("12345678-9");
        requestDTO.setNombre("Juan Perez");
        requestDTO.setCargo("Ejecutivo");
        requestDTO.setEmail("juan.perez@empresa.cl");
        requestDTO.setSueldo(new BigDecimal("850000"));
        requestDTO.setActivo(true);
        requestDTO.setFechaIngreso(LocalDate.of(2024, 3, 15));
    }

    @Test
    @DisplayName("Debe listar empleados con HATEOAS")
    void testFindAllV2() throws Exception {
        when(empleadoService.findAll()).thenReturn(List.of(empleadoDTO));

        mockMvc.perform(get("/api/v2/empleados"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.self.href").exists());

        verify(empleadoService, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe buscar empleado por id con HATEOAS")
    void testFindByIdV2() throws Exception {
        when(empleadoService.findById(1)).thenReturn(empleadoDTO);

        mockMvc.perform(get("/api/v2/empleados/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan Perez"))
                .andExpect(jsonPath("$._links.self.href").exists());

        verify(empleadoService, times(1)).findById(1);
    }

    @Test
    @DisplayName("Debe crear empleado con HATEOAS")
    void testSaveV2() throws Exception {
        when(empleadoService.save(any(EmpleadoRequestDTO.class))).thenReturn(empleadoDTO);

        mockMvc.perform(post("/api/v2/empleados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Juan Perez"))
                .andExpect(jsonPath("$._links.self.href").exists());

        verify(empleadoService, times(1)).save(any(EmpleadoRequestDTO.class));
    }

    @Test
    @DisplayName("Debe actualizar empleado con HATEOAS")
    void testUpdateV2() throws Exception {
        when(empleadoService.update(eq(1), any(EmpleadoRequestDTO.class))).thenReturn(empleadoDTO);

        mockMvc.perform(put("/api/v2/empleados/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan Perez"))
                .andExpect(jsonPath("$._links.self.href").exists());

        verify(empleadoService, times(1)).update(eq(1), any(EmpleadoRequestDTO.class));
    }

    @Test
    @DisplayName("Debe eliminar empleado")
    void testDeleteV2() throws Exception {
        doNothing().when(empleadoService).delete(1);

        mockMvc.perform(delete("/api/v2/empleados/1"))
                .andExpect(status().isNoContent());

        verify(empleadoService, times(1)).delete(1);
    }

    @Test
    @DisplayName("Debe listar empleados activos por anio con HATEOAS")
    void testListarActivosPorAnioV2() throws Exception {
        when(empleadoService.listarActivosPorAnio(2024)).thenReturn(List.of(empleadoDTO));

        mockMvc.perform(get("/api/v2/activos/anio/2024"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.self.href").exists());

        verify(empleadoService, times(1)).listarActivosPorAnio(2024);
    }
}
