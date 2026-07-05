package com.duoc.msempleados.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI empleadosOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API MS Empleados")
                        .version("1.0")
                        .description("Documentacion del microservicio de empleados"));
    }
}
