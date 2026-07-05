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
 * Configuración de Swagger / OpenAPI para el microservicio de Reservas.
 * Disponible en: http://localhost:8083/swagger-ui.html
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MS Reservas - Sistema de Arriendo de Vehículos")
                        .description("""
                                API REST del microservicio de Reservas.
                                
                                Permite gestionar reservas de vehículos y sus estados.
                                Se comunica con ms-clientes (puerto 8081) y ms-vehiculos (puerto 8082)
                                para validar la existencia del cliente y la disponibilidad del vehículo
                                antes de confirmar una reserva.
                                
                                **Recursos disponibles:**
                                - `/api/v1/reservas` — CRUD completo de reservas
                                - `/api/v1/estados-reserva` — CRUD de estados de reserva
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipo Desarrollo FullStack")
                                .email("equipo@duoc.cl"))
                        .license(new License()
                                .name("DuocUC - DSY1103")
                                .url("https://www.duoc.cl")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8083")
                                .description("Servidor local de desarrollo")
                ));
    }
}