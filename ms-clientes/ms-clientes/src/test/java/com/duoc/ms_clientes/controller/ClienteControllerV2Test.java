package com.duoc.ms_clientes.controller;

import com.duoc.ms_clientes.assemblers.ClienteModelAssembler;
import com.duoc.ms_clientes.dto.ClienteDTO;
import com.duoc.ms_clientes.dto.ClienteRequestDTO;
import com.duoc.ms_clientes.exception.GlobalExceptionHandler;
import com.duoc.ms_clientes.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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

@WebMvcTest(ClienteControllerV2.class)
@Import(GlobalExceptionHandler.class)
class ClienteControllerV2Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClienteService clienteService;

    @MockBean
    private ClienteModelAssembler clienteModelAssembler;

    private ClienteDTO clienteDTO;
    private ClienteRequestDTO requestDTO;
    private EntityModel<ClienteDTO> clienteModel;

    @BeforeEach
    void setUp() {
        clienteDTO = new ClienteDTO(1, "12345678-9", "Juan", "Perez",
                "juan.perez@gmail.com", 987654321, true, LocalDate.now());

        requestDTO = new ClienteRequestDTO("12345678-9", "Juan", "Perez",
                "juan.perez@gmail.com", 987654321, true, LocalDate.now());

        clienteModel = EntityModel.of(
                clienteDTO,
                linkTo(methodOn(ClienteControllerV2.class).findById(1)).withSelfRel()
        );
    }

    @Test
    @DisplayName("Debe listar clientes desde la ruta V2 con enlaces HATEOAS")
    void findAll_ReturnsOkWithLinks() throws Exception {
        when(clienteService.findAll()).thenReturn(List.of(clienteDTO));
        when(clienteModelAssembler.toModel(clienteDTO)).thenReturn(clienteModel);

        mockMvc.perform(get("/api/v2/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.clienteDTOList[0].id").value(1))
                .andExpect(jsonPath("$._links.self.href").exists());

        verify(clienteService).findAll();
    }

    @Test
    @DisplayName("Debe buscar un cliente por ID desde la ruta V2 con enlaces HATEOAS")
    void findById_ReturnsOkWithLinks() throws Exception {
        when(clienteService.findById(1)).thenReturn(clienteDTO);
        when(clienteModelAssembler.toModel(clienteDTO)).thenReturn(clienteModel);

        mockMvc.perform(get("/api/v2/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$._links.self.href").exists());

        verify(clienteService).findById(1);
    }

    @Test
    @DisplayName("Debe crear un cliente desde la ruta V2 con enlaces HATEOAS")
    void save_ReturnsCreatedWithLinks() throws Exception {
        when(clienteService.save(any(ClienteRequestDTO.class))).thenReturn(clienteDTO);
        when(clienteModelAssembler.toModel(clienteDTO)).thenReturn(clienteModel);

        mockMvc.perform(post("/api/v2/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$._links.self.href").exists());

        verify(clienteService).save(any(ClienteRequestDTO.class));
    }

    @Test
    @DisplayName("Debe actualizar un cliente desde la ruta V2 con enlaces HATEOAS")
    void update_ReturnsOkWithLinks() throws Exception {
        when(clienteService.update(eq(1), any(ClienteRequestDTO.class))).thenReturn(clienteDTO);
        when(clienteModelAssembler.toModel(clienteDTO)).thenReturn(clienteModel);

        mockMvc.perform(put("/api/v2/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$._links.self.href").exists());

        verify(clienteService).update(eq(1), any(ClienteRequestDTO.class));
    }

    @Test
    @DisplayName("Debe eliminar un cliente desde la ruta V2")
    void delete_ReturnsNoContent() throws Exception {
        doNothing().when(clienteService).delete(1);

        mockMvc.perform(delete("/api/v2/clientes/1"))
                .andExpect(status().isNoContent());

        verify(clienteService).delete(1);
    }

    @Test
    @DisplayName("Debe buscar clientes por email desde la ruta V2 con enlaces HATEOAS")
    void buscarPorEmail_ReturnsOkWithLinks() throws Exception {
        when(clienteService.buscarPorEmail("gmail")).thenReturn(List.of(clienteDTO));
        when(clienteModelAssembler.toModel(clienteDTO)).thenReturn(clienteModel);

        mockMvc.perform(get("/api/v2/clientes/buscar-email").param("texto", "gmail"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.clienteDTOList[0].email").value("juan.perez@gmail.com"))
                .andExpect(jsonPath("$._links.self.href").exists());

        verify(clienteService).buscarPorEmail("gmail");
    }
}