package com.example.userservice.config.auth.strategy

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.MDC
import org.springframework.stereotype.Component
private val logger = KotlinLogging.logger {}
@Component
class SsoAuthStrategy : AuthStrategy  {
    override fun authenticate(request: HttpServletRequest): AuthenticationResult {
        MDC.put("authType", "SSO")
        logger.atWarn { "NOT_APPLICABLE" }
        return AuthenticationResult(AuthStatus.NOT_APPLICABLE)
    }
}