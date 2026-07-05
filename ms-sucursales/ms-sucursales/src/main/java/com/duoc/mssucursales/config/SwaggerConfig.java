package com.duoc.mssucursales.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI msSucursalesOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MS Sucursales API")
                        .description("Documentacion del microservicio de sucursales")
                        .version("v1"));
    }
}