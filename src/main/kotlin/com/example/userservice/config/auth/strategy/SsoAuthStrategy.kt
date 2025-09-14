package com.example.userservice.config.auth.strategy

import com.example.auth_api.AuthStatus
import com.example.auth_api.AuthStrategy
import com.example.auth_api.AuthType
import com.example.auth_api.AuthenticationResult
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.MDC
import org.springframework.stereotype.Component
private val logger = KotlinLogging.logger {}
@Component
class SsoAuthStrategy : AuthStrategy {
    override fun authenticate(request: HttpServletRequest): AuthenticationResult {
        MDC.put("authType", "SSO")
        logger.atWarn { "NOT_APPLICABLE" }
        return AuthenticationResult(AuthStatus.NOT_APPLICABLE)
    }

    override fun type(): AuthType {
        return AuthType.SSO
    }
}