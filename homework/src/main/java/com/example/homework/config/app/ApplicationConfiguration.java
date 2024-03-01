package com.example.homework.config.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public Map<String, String> violationsMap() {
        return Map.of(
                "users_username_key", "A user with that name already exists"
        );
    }

}
