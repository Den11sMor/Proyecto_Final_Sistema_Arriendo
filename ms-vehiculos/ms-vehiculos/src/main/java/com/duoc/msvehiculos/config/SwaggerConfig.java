package com.duoc.msvehiculos.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI vehiculosOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MS Vehiculos API")
                        .description("Documentacion de endpoints para vehiculos y categorias")
                        .version("1.0.0"));
    }
}
