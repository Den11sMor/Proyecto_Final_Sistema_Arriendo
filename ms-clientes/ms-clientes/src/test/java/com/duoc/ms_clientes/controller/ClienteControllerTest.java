package com.duoc.ms_clientes.controller;

import com.duoc.ms_clientes.dto.ClienteDTO;
import com.duoc.ms_clientes.dto.ClienteRequestDTO;
import com.duoc.ms_clientes.exception.GlobalExceptionHandler;
import com.duoc.ms_clientes.service.ClienteService;
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

@WebMvcTest(ClienteController.class)
@Import(GlobalExceptionHandler.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClienteService clienteService;

    private ClienteDTO clienteDTO;
    private ClienteRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        clienteDTO = new ClienteDTO(1, "12345678-9", "Juan", "Perez",
                "juan.perez@gmail.com", 987654321, true, LocalDate.now());

        requestDTO = new ClienteRequestDTO("12345678-9", "Juan", "Perez",
                "juan.perez@gmail.com", 987654321, true, LocalDate.now());
    }

    @Test
    void testFindAll() throws Exception {
        when(clienteService.findAll()).thenReturn(List.of(clienteDTO));

        mockMvc.perform(get("/api/v1/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Juan"));

        verify(clienteService, times(1)).findAll();
    }

    @Test
    void testFindById() throws Exception {
        when(clienteService.findById(1)).thenReturn(clienteDTO);

        mockMvc.perform(get("/api/v1/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan"));

        verify(clienteService, times(1)).findById(1);
    }

    @Test
    void testSave() throws Exception {
        when(clienteService.save(any(ClienteRequestDTO.class))).thenReturn(clienteDTO);

        mockMvc.perform(post("/api/v1/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Juan"));

        verify(clienteService, times(1)).save(any(ClienteRequestDTO.class));
    }

    @Test
    void testUpdate() throws Exception {
        when(clienteService.update(eq(1), any(ClienteRequestDTO.class))).thenReturn(clienteDTO);

        mockMvc.perform(put("/api/v1/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan"));

        verify(clienteService, times(1)).update(eq(1), any(ClienteRequestDTO.class));
    }

    @Test
    void testDelete() throws Exception {
        doNothing().when(clienteService).delete(1);

        mockMvc.perform(delete("/api/v1/clientes/1"))
                .andExpect(status().isNoContent());

        verify(clienteService, times(1)).delete(1);
    }

    @Test
    void testBuscarPorEmail() throws Exception {
        when(clienteService.buscarPorEmail("gmail")).thenReturn(List.of(clienteDTO));

        mockMvc.perform(get("/api/v1/clientes/buscar-email").param("texto", "gmail"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("juan.perez@gmail.com"));

        verify(clienteService, times(1)).buscarPorEmail("gmail");
    }
}
