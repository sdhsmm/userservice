package com.example.userservice.dto

import com.example.userservice.entity.User
import com.example.userservice.entity.UserProfile

data class UserRegisterRequest(
    val email: String,
    val password: String,
    val username: String?,
    val firstName: String?,
    val lastName: String?,
)

fun UserRegisterRequest.toWriteUserProfileDTO(): WriteUserProfileDTO =
    WriteUserProfileDTO(
        firstName = this.firstName,
        lastName = this.lastName
    )

data class LoginRequest(
    val email: String,
    val password: String,
)

data class AuthResponse(
    val accessToken: String,
    val tokenType: String = "Bearer",
    val expiresIn: Long,
    val refreshToken: String? = null,
)

// dto/UserDtos.kt
data class UserResponse(
    val id: Long,
    val email: String,
    val username: String,
    val firstName: String?,
    val lastName: String?,
    val roles: Set<String>,
)

data class UpdateProfileRequest(
    val firstName: String?,
    val lastName: String?,
)

data class ErrorResponse(
    val timestamp: String,
    val status: Int,
    val error: String,
    val message: String?,
    val path: String
)

