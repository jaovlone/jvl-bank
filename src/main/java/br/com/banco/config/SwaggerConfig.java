package br.com.banco.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI bancoOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("JVL Bank API")
                        .description("API desenvolvida em Spring Boot para gerenciamento bancário.")
                        .version("1.0.0"));
    }
}
