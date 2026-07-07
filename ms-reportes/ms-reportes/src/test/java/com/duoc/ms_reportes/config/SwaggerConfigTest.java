package com.duoc.ms_reportes.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SwaggerConfigTest {

    @Test
    @DisplayName("Debe crear configuracion OpenAPI de reportes")
    void debeCrearOpenApiDeReportes() {
        SwaggerConfig config = new SwaggerConfig();

        OpenAPI openAPI = config.msReportesOpenAPI();

        assertNotNull(openAPI);
        assertNotNull(openAPI.getInfo());
        assertEquals("MS Reportes API", openAPI.getInfo().getTitle());
        assertEquals("1.0.0", openAPI.getInfo().getVersion());
        assertNotNull(openAPI.getServers());
    }
}