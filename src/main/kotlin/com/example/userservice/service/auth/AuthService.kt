package com.example.userservice.service.auth

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
import org.springframework.security.core.context.SecurityContextHolder

interface AuthService
fun getLoggedInUser(): CurrentUser {
    val authentication = SecurityContextHolder.getContext().authentication
    if (authentication == null || !authentication.isAuthenticated) {
        throw AuthenticationCredentialsNotFoundException("User is not logged in")
    }
    return authentication.principal as CurrentUser
}
