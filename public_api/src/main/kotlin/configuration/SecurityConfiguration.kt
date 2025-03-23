package com.moineaufactory.lingvasferoapi.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val apiKeyAuthFilter: ApiKeyAuthFilter
) {

    @get:Bean
    val corsConfiguration: CorsConfigurationSource get() {
        val configuration = CorsConfiguration()
        configuration.allowedMethods = listOf("GET")
        configuration.allowedOrigins = listOf("http://localhost:5173")
        configuration.allowedHeaders = listOf("x-api-key", "X-API-KEY")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .addFilterBefore(apiKeyAuthFilter, BasicAuthenticationFilter::class.java)
            .authorizeHttpRequests { auth ->
                auth.anyRequest().permitAll() // Secure all endpoints
            }
            .cors { c -> c.configurationSource(corsConfiguration) }
            .csrf { it.disable() } // Disable CSRF for API usage
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) } // API should be stateless
            .httpBasic { it.disable() } // Disables default basic authentication
            .formLogin { it.disable() } // Disables form login
        return http.build()
    }

}