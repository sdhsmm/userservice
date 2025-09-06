package com.example.userservice.config.auth.strategy

import jakarta.servlet.http.HttpServletRequest

interface AuthStrategy {
    fun authenticate(request: HttpServletRequest): AuthenticationResult
}
