package com.example.userservice.dto

data class UserRegisterRequest(
    val email: String,
    val password: String,
    val username: String?,
    val firstName: String?,
    val lastName: String?
)

data class LoginRequest(val email: String, val password: String)

data class AuthResponse(
    val accessToken: String,
    val tokenType: String = "Bearer",
    val expiresIn: Long,
    val refreshToken: String? = null
)

// dto/UserDtos.kt
data class UserResponse(
    val id: Long,
    val email: String,
    val username: String,
    val firstName: String?,
    val lastName: String?,
    val roles: Set<String>
)

data class UpdateProfileRequest(
    val firstName: String?,
    val lastName: String?
)