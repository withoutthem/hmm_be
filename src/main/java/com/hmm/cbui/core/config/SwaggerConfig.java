/* (C) 2025 HMM Corp. All rights reserved. */
package com.hmm.cbui.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
  @Bean
  public OpenAPI openAPI() {
    Info info =
        new Info().title("HMM API").version("v1.0").description("Spring Boot API Documentation");
    return new OpenAPI().info(info);
  }
}
