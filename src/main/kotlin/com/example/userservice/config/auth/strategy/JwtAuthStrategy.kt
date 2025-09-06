package com.example.userservice.config.auth.strategy

import com.example.userservice.config.JwtUtils
import com.example.userservice.service.auth.CustomUserDetailsService
import com.example.userservice.service.auth.TokenBlacklistService
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.MDC
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import kotlin.text.removePrefix
import kotlin.text.startsWith


private val logger = KotlinLogging.logger {}

@Component
class JwtAuthStrategy(
    private val jwtUtil: JwtUtils,
    private val customUserDetailsService: CustomUserDetailsService,
    private val tokenBlacklistService: TokenBlacklistService,
) : AuthStrategy {

    override fun authenticate(request: HttpServletRequest): AuthenticationResult {
        MDC.put("authType", "JWT")
        // Extract token from header
        val authHeader = request.getHeader("Authorization")
        if (authHeader.isNullOrBlank() || !authHeader.startsWith("Bearer ")) {
            return AuthenticationResult(
                status = AuthStatus.FAILURE,
                errorMessage = "Missing or invalid Authorization header"
            )
        }
        var username: String? = null
        val userDetails: UserDetails? = null
        val token = authHeader.removePrefix("Bearer ").trim()
        try {
            username = jwtUtil.parseClaims(token).subject
        } catch (e: Exception) {
            logger.error(e) { "JWT parsing error: " }
        }

        // Validate token
        if (username != null && SecurityContextHolder.getContext().authentication == null) {
            val userDetails = customUserDetailsService.loadUserByUsername(username)
            if (!jwtUtil.validateToken(token, userDetails) || tokenBlacklistService.isTokenBlacklisted(token)) {
                return AuthenticationResult(
                    status = AuthStatus.FAILURE,
                    errorMessage = "Invalid or expired JWT",
                )
            }
        }


        return AuthenticationResult(
            status = AuthStatus.SUCCESS,
            userDetails = userDetails
        )
    }
}
