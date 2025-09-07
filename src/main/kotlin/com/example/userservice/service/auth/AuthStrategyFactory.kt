package com.example.userservice.service.auth

import com.example.auth_api.AuthStrategy
import com.example.auth_api.AuthType
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class AuthStrategyFactory(
    private val authStrategies: List<AuthStrategy>,
    @Value("\${app.auth.types}") private val authTypes: List<String>
) {
    fun getStrategies(): List<AuthStrategy> {
        val lookup = authStrategies.associateBy { it.type() }
        return authTypes.mapNotNull {
            when (it.lowercase()) {
                "basic" -> lookup[AuthType.BASIC]
                "jwt" -> lookup[AuthType.JWT]
                "sso" -> lookup[AuthType.SSO]
                else -> throw IllegalArgumentException("Invalid auth type: $it")
            }
        }
    }
}
