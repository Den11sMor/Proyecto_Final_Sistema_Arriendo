package com.duoc.msempleados.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configura la documentacion OpenAPI del microservicio de empleados.
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI empleadosOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MS Empleados API")
                        .description("""
                                API REST del microservicio de empleados

                                Permite gestionar empleados del sistema
                                Entrega informacion para control operativo y consultas por anio

                                Recursos disponibles
                                - /api/v1/empleados
                                - /api/v1/activos/anio/{anio}
                                - /api/v2/empleados
                                - /api/v2/activos/anio/{anio}
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
                                .url("http://localhost:8086")
                                .description("Servidor local de desarrollo")
                ));
    }
}