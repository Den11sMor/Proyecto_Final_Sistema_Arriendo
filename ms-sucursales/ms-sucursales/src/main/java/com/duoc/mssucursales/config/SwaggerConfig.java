package com.duoc.mssucursales.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configura la documentacion OpenAPI del microservicio de sucursales
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI msSucursalesOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MS Sucursales API")
                        .description("Documentacion del microservicio de sucursales. Expone endpoints para administrar regiones y sucursales.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipo Desarrollo FullStack")
                                .email("equipo@duoc.cl"))
                        .license(new License()
                                .name("DuocUC DSY1103")
                                .url("https://www.duoc.cl")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8085")
                                .description("Servidor local de ms-sucursales"),
                        new Server()
                                .url("http://localhost:8080/ms-sucursales")
                                .description("Acceso por API Gateway")
                ));
    }
}