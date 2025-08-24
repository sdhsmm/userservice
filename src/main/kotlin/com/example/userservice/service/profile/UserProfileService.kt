package com.example.userservice.service.profile

import com.example.userservice.dto.WriteUserProfileDTO
import com.example.userservice.entity.UserProfile

interface UserProfileService {
    fun getUserProfileByUserId(id: Long): UserProfile
    fun getUserProfileByOrgId(orgId: String): UserProfile
    fun createUserProfile(userId: Long, writeUserProfileDTO: WriteUserProfileDTO): UserProfile
    fun updateUserProfile(userId: Long, writeUserProfileDTO: WriteUserProfileDTO): UserProfile
}
