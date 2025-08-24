package com.example.userservice.service.auth

import com.example.userservice.dto.AuthResponse
import com.example.userservice.dto.LoginRequest
import com.example.userservice.dto.UserRegisterRequest
import com.example.userservice.entity.User
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
import org.springframework.security.core.context.SecurityContextHolder

interface AuthService {

    fun register(userRegisterDto: UserRegisterRequest): User

    fun login(req: LoginRequest): ResponseEntity<AuthResponse>

    fun logout(request: HttpServletRequest): ResponseEntity<String>
}
fun getLoggedInUser(): CurrentUser {
    val authentication = SecurityContextHolder.getContext().authentication
    if (authentication == null || !authentication.isAuthenticated) {
        throw AuthenticationCredentialsNotFoundException("User is not logged in")
    }
    return authentication.principal as CurrentUser
}
