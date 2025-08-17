package com.example.userservice.dto

import com.example.userservice.enum.RoleType

data class ReadUserProfileDTO(
    val email: String,
    val username: String,
    val firstName: String?,
    val lastName: String?,
    val enabled: Boolean,
    val locked: Boolean,
    val roles: Set<RoleType> // only role names, not full Role entities
)


data class WriteUserProfileDTO(
    val email: String,
    val username: String,
    val firstName: String?,
    val lastName: String?
)