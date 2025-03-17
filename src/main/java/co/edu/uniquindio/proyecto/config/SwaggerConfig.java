package co.edu.uniquindio.proyecto.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Plataforma de Reportes Ciudadanos")
                        .version("1.2.0")
                        .description("API para gestión de reportes ciudadanos"));
    }
}