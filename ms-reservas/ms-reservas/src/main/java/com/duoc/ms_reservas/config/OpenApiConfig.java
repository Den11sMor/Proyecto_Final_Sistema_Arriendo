package com.duoc.ms_reservas.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configura la documentacion OpenAPI del microservicio de reservas
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MS Reservas API")
                        .description("""
                                API REST del microservicio de reservas

                                Permite gestionar reservas y estados de reserva
                                Se comunica con ms-clientes y ms-vehiculos para validar datos externos

                                Recursos disponibles
                                - /api/v1/reservas
                                - /api/v1/estados-reserva
                                - /api/v2/reservas
                                - /api/v2/estados-reserva
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
                                .url("http://localhost:8083")
                                .description("Servidor local de desarrollo")
                ));
    }
}