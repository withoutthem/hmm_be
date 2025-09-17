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
        new Info()
            .title("HMM ChatBot API")
            .version("v1.0")
            .description("HMM 생성형 챗봇UI에서 사용하는 모든 API모음 입니다.");
    return new OpenAPI().info(info);
  }
}
