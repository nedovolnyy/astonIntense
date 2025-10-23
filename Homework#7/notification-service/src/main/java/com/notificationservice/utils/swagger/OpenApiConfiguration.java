package com.notificationservice.utils.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AllArgsConstructor
@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI openAPI() {
        var info = new Info()
                .title("OpenAPI for notification-service")
                .description("Provides send message about create/delete operation");
        return new OpenAPI().info(info);
    }
}
