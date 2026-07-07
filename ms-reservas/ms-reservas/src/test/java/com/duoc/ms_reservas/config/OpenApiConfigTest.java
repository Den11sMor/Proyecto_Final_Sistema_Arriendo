package com.duoc.ms_reservas.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Prueba la configuracion OpenAPI del microservicio.
 */
class OpenApiConfigTest {

    @Test
    void openAPI_ReturnsConfiguredDocumentation() {
        OpenApiConfig config = new OpenApiConfig();

        OpenAPI openAPI = config.openAPI();

        assertNotNull(openAPI);
        assertNotNull(openAPI.getInfo());
        assertEquals("MS Reservas API", openAPI.getInfo().getTitle());
        assertEquals("1.0.0", openAPI.getInfo().getVersion());
    }
}