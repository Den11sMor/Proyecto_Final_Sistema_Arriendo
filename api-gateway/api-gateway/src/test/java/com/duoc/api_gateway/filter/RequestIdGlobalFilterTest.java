package com.duoc.api_gateway.filter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class RequestIdGlobalFilterTest {

    @Test
    void debeCrearFiltroGlobal() {
        RequestIdGlobalFilter config = new RequestIdGlobalFilter();

        assertNotNull(config.addRequestIdHeaderFilter());
    }
}
