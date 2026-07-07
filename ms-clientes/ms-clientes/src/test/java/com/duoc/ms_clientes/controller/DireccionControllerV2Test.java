package com.duoc.ms_clientes.controller;

import com.duoc.ms_clientes.assemblers.DireccionModelAssembler;
import com.duoc.ms_clientes.dto.DireccionDTO;
import com.duoc.ms_clientes.dto.DireccionRequestDTO;
import com.duoc.ms_clientes.exception.GlobalExceptionHandler;
import com.duoc.ms_clientes.service.DireccionService;
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

@WebMvcTest(DireccionControllerV2.class)
@Import(GlobalExceptionHandler.class)
class DireccionControllerV2Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DireccionService direccionService;

    @MockBean
    private DireccionModelAssembler direccionModelAssembler;

    private DireccionDTO direccionDTO;
    private DireccionRequestDTO requestDTO;
    private EntityModel<DireccionDTO> direccionModel;

    @BeforeEach
    void setUp() {
        direccionDTO = new DireccionDTO(1, "Av Siempre Viva", 742, "Santiago",
                "Santiago", "Casa azul", true, LocalDate.now(), 1);

        requestDTO = new DireccionRequestDTO("Av Siempre Viva", 742, "Santiago",
                "Santiago", "Casa azul", true, LocalDate.now(), 1);

        direccionModel = EntityModel.of(
                direccionDTO,
                linkTo(methodOn(DireccionControllerV2.class).findById(1)).withSelfRel()
        );
    }

    @Test
    @DisplayName("Debe listar direcciones desde la ruta V2 con enlaces HATEOAS")
    void findAll_ReturnsOkWithLinks() throws Exception {
        when(direccionService.findAll()).thenReturn(List.of(direccionDTO));
        when(direccionModelAssembler.toModel(direccionDTO)).thenReturn(direccionModel);

        mockMvc.perform(get("/api/v2/direcciones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.direccionDTOList[0].id").value(1))
                .andExpect(jsonPath("$._links.self.href").exists());

        verify(direccionService).findAll();
    }

    @Test
    @DisplayName("Debe buscar una direccion por ID desde la ruta V2 con enlaces HATEOAS")
    void findById_ReturnsOkWithLinks() throws Exception {
        when(direccionService.findById(1)).thenReturn(direccionDTO);
        when(direccionModelAssembler.toModel(direccionDTO)).thenReturn(direccionModel);

        mockMvc.perform(get("/api/v2/direcciones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$._links.self.href").exists());

        verify(direccionService).findById(1);
    }

    @Test
    @DisplayName("Debe crear una direccion desde la ruta V2 con enlaces HATEOAS")
    void save_ReturnsCreatedWithLinks() throws Exception {
        when(direccionService.save(any(DireccionRequestDTO.class))).thenReturn(direccionDTO);
        when(direccionModelAssembler.toModel(direccionDTO)).thenReturn(direccionModel);

        mockMvc.perform(post("/api/v2/direcciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$._links.self.href").exists());

        verify(direccionService).save(any(DireccionRequestDTO.class));
    }

    @Test
    @DisplayName("Debe actualizar una direccion desde la ruta V2 con enlaces HATEOAS")
    void update_ReturnsOkWithLinks() throws Exception {
        when(direccionService.update(eq(1), any(DireccionRequestDTO.class))).thenReturn(direccionDTO);
        when(direccionModelAssembler.toModel(direccionDTO)).thenReturn(direccionModel);

        mockMvc.perform(put("/api/v2/direcciones/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$._links.self.href").exists());

        verify(direccionService).update(eq(1), any(DireccionRequestDTO.class));
    }

    @Test
    @DisplayName("Debe eliminar una direccion desde la ruta V2")
    void delete_ReturnsNoContent() throws Exception {
        doNothing().when(direccionService).delete(1);

        mockMvc.perform(delete("/api/v2/direcciones/1"))
                .andExpect(status().isNoContent());

        verify(direccionService).delete(1);
    }
}
