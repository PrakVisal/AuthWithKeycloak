package com.example.springmini2.config;

import feign.Feign;
import feign.codec.Encoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakAdminFeignConfig {
    // Use default Jackson encoder explicitly
    @Bean
    public Encoder feignEncoder() {
        return new feign.jackson.JacksonEncoder();
    }
}
