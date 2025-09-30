package com.example.springmini2.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Allow the to access without authentication
    private final String[] WHITE_LIST_URL =
            new String[] {
                    "/api/v1/auth/**", // Authentication APIs (login, register, etc.)
                    "/v3/api-docs/**", // OpenAPI docs
                    "/swagger-ui/**", // Swagger UI
                    "/swagger-ui.html",
                    "/actuator/**",
                    "/no-auth/**"
            };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests(
                req
                        -> req
                        .requestMatchers(WHITE_LIST_URL).permitAll()
                        .anyRequest().authenticated());
        http.oauth2ResourceServer(oauth->
                oauth.jwt(Customizer.withDefaults()));
        return http.build();
    }
}
