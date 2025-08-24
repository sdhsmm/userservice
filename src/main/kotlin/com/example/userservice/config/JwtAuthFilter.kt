package com.example.userservice.config

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
class JwtAuthFilter(
    private val jwtUtil: JwtUtils,
    private val customUserDetailsService: CustomUserDetailsService,
    private val tokenBlacklistService: TokenBlacklistService,
) : OncePerRequestFilter() {
    @Throws(ServletException::class, java.io.IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val authHeader = request.getHeader("Authorization")
        var username: String? = null
        var token: String? = null

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7)
            try {
                username = jwtUtil.parseClaims(token).subject
            } catch (e: Exception) {
                logger.error("JWT parsing error: ", e)
            }
        }

        if (username != null && SecurityContextHolder.getContext().authentication == null) {
            val userDetails = customUserDetailsService.loadUserByUsername(username)

            if (jwtUtil.validateToken(token!!, userDetails) && !tokenBlacklistService.isTokenBlacklisted(token)) {
                val authToken =
                    UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.authorities,
                    )
                authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authToken
            }
        }

        filterChain.doFilter(request, response)
    }
}
