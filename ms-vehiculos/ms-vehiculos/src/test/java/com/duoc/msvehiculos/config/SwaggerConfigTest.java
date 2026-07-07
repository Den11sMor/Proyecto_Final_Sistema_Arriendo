package com.duoc.msvehiculos.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Pruebas de configuracion OpenAPI para Swagger.
 */
class SwaggerConfigTest {

    @Test
    void debeCrearOpenApiDeVehiculos() {
        SwaggerConfig config = new SwaggerConfig();

        OpenAPI openAPI = config.vehiculosOpenAPI();

        assertNotNull(openAPI);
        assertNotNull(openAPI.getInfo());
        assertEquals("MS Vehiculos API", openAPI.getInfo().getTitle());
        assertEquals("1.0.0", openAPI.getInfo().getVersion());
    }
}