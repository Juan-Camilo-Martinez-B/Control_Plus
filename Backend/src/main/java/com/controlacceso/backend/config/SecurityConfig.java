package com.controlacceso.backend.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Configuración de seguridad de la aplicación
 * Gestiona autenticación y políticas de CORS
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String[] PUBLIC_ENDPOINTS = {
            "/api/auth/**",
            "/public/**",
            "/error",
            "/actuator/health"
    };

    private static final String[] ADMIN_ENDPOINTS = {
            "/api/admin/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Deshabilitar CSRF para APIs REST (se recomienda usar tokens JWT)
            .csrf(csrf -> csrf.disable())
            
            // Configuración de CORS
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            
            // Política de sesiones stateless (sin sesiones en servidor)
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            
            // Configuración de autorización de endpoints
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                .requestMatchers(ADMIN_ENDPOINTS).hasRole("ADMIN")
                .anyRequest().permitAll() // Permitir todas las demás solicitudes por ahora
            );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Permitir orígenes específicos (cambiar en producción)
        configuration.setAllowedOrigins(List.of(
                "http://localhost:3000",  // Frontend Next.js local
                "http://localhost:8081"   // Backend local
        ));
        
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        return source;
    }
}
