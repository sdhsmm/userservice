package com.example.userservice.service.auth

import com.example.userservice.config.auth.strategy.AuthStrategy
import com.example.userservice.config.auth.strategy.BasicAuthStrategy
import com.example.userservice.config.auth.strategy.JwtAuthStrategy
import com.example.userservice.config.auth.strategy.SsoAuthStrategy
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class AuthStrategyFactory(
    private val jwtAuth: JwtAuthStrategy,
    private val basicAuth: BasicAuthStrategy,
    private val ssoAuth: SsoAuthStrategy,
    @Value("\${auth.types}") private val authTypes: List<String>
) {
    fun getStrategies(): List<AuthStrategy> =
        authTypes.map {
            when (it.lowercase()) {
                "basic" -> basicAuth
                "jwt" -> jwtAuth
                "sso" -> ssoAuth
                else -> throw IllegalArgumentException("Invalid auth type: $it")
            }
        }
}
