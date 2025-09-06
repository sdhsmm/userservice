package com.example.userservice.config.auth.strategy

import org.springframework.security.core.userdetails.UserDetails

enum class AuthStatus {
    SUCCESS,
    FAILURE,
    NOT_APPLICABLE
}
data class AuthenticationResult(
    val status: AuthStatus,
    val errorMessage: String? = null, // reason for failure (if any)
    val userDetails: UserDetails? = null
){
     val isAuthenticated = status == AuthStatus.SUCCESS
}

