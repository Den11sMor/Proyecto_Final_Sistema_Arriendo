package com.duoc.ms_clientes.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configura la documentacion OpenAPI del microservicio de clientes.
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI clientesOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MS Clientes API")
                        .description("""
                                API REST del microservicio de clientes

                                Permite gestionar clientes y direcciones
                                Entrega datos usados por otros microservicios como reservas

                                Recursos disponibles
                                - /api/v1/clientes
                                - /api/v1/direcciones
                                - /api/v2/clientes
                                - /api/v2/direcciones
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
                                .url("http://localhost:8081")
                                .description("Servidor local de desarrollo")
                ));
    }
}
