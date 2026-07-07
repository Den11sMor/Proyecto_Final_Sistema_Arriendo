package com.duoc.msempleados.config;

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
    @DisplayName("Debe crear OpenAPI de empleados")
    void debeCrearOpenApiDeEmpleados() {
        SwaggerConfig config = new SwaggerConfig();

        OpenAPI openAPI = config.empleadosOpenAPI();

        assertNotNull(openAPI);
        assertNotNull(openAPI.getInfo());
        assertEquals("MS Empleados API", openAPI.getInfo().getTitle());
        assertEquals("1.0.0", openAPI.getInfo().getVersion());
    }
}
