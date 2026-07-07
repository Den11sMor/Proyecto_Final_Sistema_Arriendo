package com.duoc.mssucursales.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SwaggerConfigTest {

    @Test
    @DisplayName("Debe crear configuracion OpenAPI de sucursales")
    void debeCrearOpenApiDeSucursales() {
        SwaggerConfig config = new SwaggerConfig();

        OpenAPI openAPI = config.msSucursalesOpenAPI();

        assertNotNull(openAPI);
        assertNotNull(openAPI.getInfo());
        assertEquals("MS Sucursales API", openAPI.getInfo().getTitle());
        assertEquals("1.0.0", openAPI.getInfo().getVersion());
        assertNotNull(openAPI.getServers());
    }
}