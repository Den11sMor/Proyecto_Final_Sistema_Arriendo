package com.duoc.eureka_server.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI eurekaServerOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Eureka Server API")
                        .description("Documentacion del servidor Eureka")
                        .version("v1"));
    }
}
