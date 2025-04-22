package co.edu.uniquindio.proyecto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Permitir acceso sin autenticación a todas las rutas (temporal)
                )
                .formLogin(form -> form.disable()) // Deshabilitar formulario de login (temporal)
                .httpBasic(httpBasic -> httpBasic.disable()); // Deshabilitar autenticación básica

        return http.build();
    }
}

