package com.duoc.api_gateway.filter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class RequestIdGlobalFilterTest {

    @Test
    @DisplayName("Debe crear filtro global de request id")
    void debeCrearFiltroGlobal() {
        RequestIdGlobalFilter config = new RequestIdGlobalFilter();

        assertNotNull(config.addRequestIdHeaderFilter());
    }
}
