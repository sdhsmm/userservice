package com.example.userservice.config

import com.example.userservice.config.auth.strategy.CompositeAuthStrategy
import com.example.userservice.service.auth.AuthStrategyFactory
import com.example.userservice.service.auth.CustomUserDetailsService
import com.example.userservice.service.auth.TokenBlacklistService
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class DynamicAuthFilter(
    private val factory: AuthStrategyFactory
) : OncePerRequestFilter() {
    @Throws(ServletException::class, java.io.IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {

        val strategies = factory.getStrategies()
        val composite = CompositeAuthStrategy(strategies)
        val result = composite.authenticate(request)

        if(result.isAuthenticated){
            val userDetails = result.userDetails
            val authToken =
                UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails?.authorities,
                )
            authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
            SecurityContextHolder.getContext().authentication = authToken
        }

        filterChain.doFilter(request, response)
    }
}
