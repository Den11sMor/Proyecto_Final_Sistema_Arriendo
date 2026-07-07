package com.duoc.ms_pagos.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configura la documentacion OpenAPI del microservicio de pagos.
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI msPagosOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MS Pagos API")
                        .description("""
                                API REST del microservicio de pagos

                                Permite gestionar pagos asociados a reservas
                                Entrega informacion para registrar, actualizar y consultar pagos

                                Recursos disponibles
                                - /api/v1/pagos
                                - /api/v1/pagos/rango
                                - /api/v2/pagos
                                - /api/v2/pagos/rango
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipo Desarrollo FullStack")
                                .email("equipo@duoc.cl"))
                        .license(new License()
                                .name("DuocUC DSY1103")
                                .url("https://www.duoc.cl")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8084")
                                .description("Servidor local de desarrollo")
                ));
    }
}