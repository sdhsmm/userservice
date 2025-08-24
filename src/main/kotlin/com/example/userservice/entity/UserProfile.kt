package com.example.userservice.entity

import com.example.userservice.dto.ReadUserProfileDTO
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "user_profile")
data class UserProfile(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    var firstName: String? = null,
    var middleName: String? = null,
    var lastName: String? = null,
    var dob: String? = null,
    var phone: String? = null,
    var address: String? = null,
    var profilePicUrl: String? = null,
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    val user: User,
)

fun UserProfile.toReadUserProfileDTO(): ReadUserProfileDTO =
    ReadUserProfileDTO(
        firstName = this.firstName ?: "",
        middleName = this.middleName ?: "",
        lastName = this.lastName ?: "",
        profilePicUrl = this.profilePicUrl ?: "",
        phone = this.phone?:"",
        address = this.address?:"",
    )
