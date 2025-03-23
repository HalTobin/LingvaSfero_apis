package com.moineaufactory.lingvasferoapi.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.User
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter


@Configuration
class SecurityConfig(private val apiKeyAuthFilter: ApiKeyAuthFilter) {

    @Value("\${ls.admin.api.username}")
    private lateinit var userName: String
    @Value("\${ls.admin.api.password}")
    private lateinit var adminPassword: String

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .addFilterBefore(apiKeyAuthFilter, BasicAuthenticationFilter::class.java)
            .authorizeHttpRequests { auth ->
                auth.anyRequest().authenticated() // Secure all endpoints
            }
            .sessionManagement {
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .httpBasic(Customizer.withDefaults())
            .csrf { it.disable() } // Disable CSRF for API usage

        return http.build()
    }

    @Bean
    fun users(): InMemoryUserDetailsManager {
        return InMemoryUserDetailsManager(
            User.withUsername(userName)
                .password("{noop}$adminPassword")
                .authorities("read")
                .build()
        )
    }

}