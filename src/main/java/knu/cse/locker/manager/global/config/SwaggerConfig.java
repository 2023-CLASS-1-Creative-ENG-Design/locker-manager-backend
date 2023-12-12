package knu.cse.locker.manager.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

/* 
 * SwaggerConfig.java
 *
 * @note Swagger 설정
 *
 */

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI swaggerSpec() {
        return new OpenAPI()
                .info(new Info().title("Locker-Manager-Swagger").version("1.0.0"))
                .components(new Components()
                        .addSecuritySchemes("Access_Token", new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")))
                .addSecurityItem(new SecurityRequirement().addList("Access_Token"));
    }
}
