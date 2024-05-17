package com.example.demo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI baseOpenAPI() {
        final String securityScheme = "bearer";
        return new OpenAPI()
                .info(
                    new Info()
                            .title("API文件")
                            .version("1.0")
                            .description("spring boot application")
                )
                .addSecurityItem(
                        new SecurityRequirement()
                                .addList(securityScheme)
                )
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        securityScheme,
                                        new SecurityScheme()
                                                .name(securityScheme)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                );
    }
}
