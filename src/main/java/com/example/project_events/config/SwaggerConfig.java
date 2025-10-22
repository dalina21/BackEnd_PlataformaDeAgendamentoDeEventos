package com.example.project_events.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customSwagger(){
        return new OpenAPI().info(new Info().title("Agendamento de Eventos API").version("1.0.0"));
    }
}
