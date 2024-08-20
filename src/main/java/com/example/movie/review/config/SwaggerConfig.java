package com.example.movie.review.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springdoc.core.GroupedOpenApi;
/**
 *
 * @author rebeca
 */
@Configuration
public class SwaggerConfig {
    
    @Bean
    public GroupedOpenApi filmesApi() {
        return GroupedOpenApi.builder()
                .group("filmes")
                .pathsToMatch("/filmes/**")
                .build();
    }
}
