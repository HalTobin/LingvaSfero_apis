package com.moineaufactory.lingvasferoapi.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/*@Configuration
class CorsConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedHeaders("Access-Control-Allow-Headers", "X-API-KEY")
            .allowedOrigins("http://localhost:5173") // Allow frontend
            .allowedMethods("GET")
    }
}*/