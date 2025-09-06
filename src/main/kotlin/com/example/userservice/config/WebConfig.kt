package com.example.userservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
class WebConfig {
    @Bean
    fun corsConfigurer(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/**") // Allow all endpoints
                    .allowedOrigins("http://localhost:5173") // Your React frontend
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowedHeaders("*") // Allow all headers
                    .allowCredentials(true) // Allow cookies/Authorization header
                    .maxAge(3600) // Cache preflight response for 1 hour
            }
        }
    }
}
