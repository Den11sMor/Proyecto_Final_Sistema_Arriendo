package com.duoc.ms_reportes.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configura la documentacion OpenAPI del microservicio de reportes
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI msReportesOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MS Reportes API")
                        .description("Documentacion del microservicio de reportes. Expone endpoints para consultar, crear, actualizar y eliminar reportes del sistema.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipo Desarrollo FullStack")
                                .email("equipo@duoc.cl"))
                        .license(new License()
                                .name("DuocUC DSY1103")
                                .url("https://www.duoc.cl")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8087")
                                .description("Servidor local de ms-reportes"),
                        new Server()
                                .url("http://localhost:8080/ms-reportes")
                                .description("Acceso por API Gateway")
                ));
    }
}