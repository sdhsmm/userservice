package com.example.userservice.config.auth.strategy

import com.example.auth_api.AuthStatus
import com.example.auth_api.AuthStrategy
import com.example.auth_api.AuthType
import com.example.auth_api.AuthenticationResult
import jakarta.servlet.http.HttpServletRequest

class CompositeAuthStrategy(
    private val strategies: List<AuthStrategy>
) : AuthStrategy {

    override fun authenticate(request: HttpServletRequest): AuthenticationResult {
        var lastFailure: AuthenticationResult? = null
        for (strategy in strategies) {
            val result = strategy.authenticate(request)
            when (result.status) {
                AuthStatus.SUCCESS -> return result
                AuthStatus.FAILURE -> lastFailure = result
                AuthStatus.NOT_APPLICABLE -> continue
            }
        }
        return AuthenticationResult(AuthStatus.FAILURE)
    }

    override fun type(): AuthType {
        return AuthType.COMPOSITE
    }
}
