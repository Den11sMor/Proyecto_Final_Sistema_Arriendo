package com.duoc.ms_clientes.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SwaggerConfigTest {

    @Test
    void clientesOpenAPI_ReturnsConfiguredDocumentation() {
        SwaggerConfig config = new SwaggerConfig();

        OpenAPI openAPI = config.clientesOpenAPI();

        assertNotNull(openAPI);
        assertNotNull(openAPI.getInfo());
        assertEquals("MS Clientes API", openAPI.getInfo().getTitle());
        assertEquals("1.0.0", openAPI.getInfo().getVersion());
    }
}