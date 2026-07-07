package com.duoc.ms_pagos.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Pruebas de configuracion OpenAPI para Swagger.
 */
@DisplayName("SwaggerConfig")
class SwaggerConfigTest {

    @Test
    @DisplayName("Debe crear OpenAPI de pagos")
    void debeCrearOpenApiDePagos() {
        SwaggerConfig config = new SwaggerConfig();

        OpenAPI openAPI = config.msPagosOpenAPI();

        assertNotNull(openAPI);
        assertNotNull(openAPI.getInfo());
        assertEquals("MS Pagos API", openAPI.getInfo().getTitle());
        assertEquals("1.0.0", openAPI.getInfo().getVersion());
    }
}