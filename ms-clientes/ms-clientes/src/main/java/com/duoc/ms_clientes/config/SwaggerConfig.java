package com.duoc.ms_clientes.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI clientesOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MS Clientes API")
                        .description("Documentacion de endpoints para clientes y direcciones")
                        .version("1.0.0"));
    }
}
