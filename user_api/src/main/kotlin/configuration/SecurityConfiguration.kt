package com.moineaufactory.lingvasferoapi.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

@Configuration
class SecurityConfig(private val apiKeyAuthFilter: ApiKeyAuthFilter) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .addFilterBefore(apiKeyAuthFilter, BasicAuthenticationFilter::class.java)
            .authorizeHttpRequests { auth ->
                auth.anyRequest().permitAll() // Secure all endpoints
            }
            .csrf { it.disable() } // Disable CSRF for API usage
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) } // API should be stateless
            .httpBasic { it.disable() } // Disables default basic authentication
            .formLogin { it.disable() } // Disables form login

        return http.build()
    }

}