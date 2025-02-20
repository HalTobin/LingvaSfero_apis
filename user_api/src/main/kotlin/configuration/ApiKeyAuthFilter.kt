package com.moineaufactory.lingvasferoapi.configuration

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.filter.GenericFilterBean

@Component
class ApiKeyAuthFilter : GenericFilterBean() {

    @Value("\${ls.admin.api.key}")
    private lateinit var validApiKey: String

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val httpRequest = request as HttpServletRequest
        val apiKey = httpRequest.getHeader("X-API-KEY")

        if (apiKey == null || apiKey != validApiKey) {
            throw SecurityException("Invalid API Key")
        }

        chain.doFilter(request, response)
    }

}