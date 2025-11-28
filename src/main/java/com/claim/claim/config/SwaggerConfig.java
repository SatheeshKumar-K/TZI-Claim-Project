package com.claim.claim.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springdoc.core.models.GroupedOpenApi;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI claimOpenAPI() {
    return new OpenAPI()
        .info(
            new Info()
                .title("Claim Management API")
                .description("API documentation for Claim Management System")
                .version("1.0"));
  }

  @Bean
  public GroupedOpenApi publicApi() {
    return GroupedOpenApi.builder().group("claims").pathsToMatch("/api/claims/**").build();
  }
}
