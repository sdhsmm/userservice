package com.example.userservice.dto

import com.example.userservice.entity.User
import com.example.userservice.entity.UserProfile
import kotlin.String

data class ReadUserProfileDTO(
    val firstName: String,
    val middleName: String,
    val lastName: String,
    var phone: String,
    var address: String,
    val profilePicUrl: String,
)

data class WriteUserProfileDTO(
    val firstName: String? = null,
    val middleName: String? = null,
    val lastName: String? = null,
    var phone: String? = null,
    var address: String? = null,
    val profilePicUrl: String? = null,
)

fun WriteUserProfileDTO.toEntity(user: User): UserProfile =
    UserProfile(
        firstName = this.firstName,
        middleName = this.middleName,
        lastName = this.lastName,
        phone = this.phone,
        address = this.address,
        profilePicUrl = this.profilePicUrl,
        user = user
    )
