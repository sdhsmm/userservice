package com.example.userservice.service.profile

import com.example.userservice.dto.WriteUserProfileDTO
import com.example.userservice.dto.toEntity
import com.example.userservice.dto.toExistingEntity
import com.example.userservice.entity.UserProfile
import com.example.userservice.exception.ResourceNotFoundException
import com.example.userservice.repository.UserProfileRepository
import com.example.userservice.service.UserService
import org.springframework.stereotype.Service

@Service
class UserProfileServiceImpl(
    private val userProfileRepository: UserProfileRepository,
    private val userService: UserService
) : UserProfileService {
    override fun getUserProfileByUserId(id: Long): UserProfile {
        val profile = userProfileRepository.findByUserId(id)
        return profile ?: throw ResourceNotFoundException("No profile found.")
    }

    override fun getUserProfileByOrgId(orgId: String): UserProfile {
        val user = userService.getUserByOrgId(orgId)
        val profile = userProfileRepository.findByUserId(user.id)
        return profile ?: throw ResourceNotFoundException("OrgId: $orgId, No profile found.")
    }

    override fun createUserProfile(
        userId: Long,
        writeUserProfileDTO: WriteUserProfileDTO
    ): UserProfile {
        val profileEntity = writeUserProfileDTO.toEntity(userService.getUserByUserId(userId))
        val savedProfile = userProfileRepository.save(profileEntity)
        return savedProfile
    }

    override fun updateUserProfile(
        userId: Long,
        writeUserProfileDTO: WriteUserProfileDTO
    ): UserProfile {
        val existingProfile = userProfileRepository.findByUserId(userId)?: throw ResourceNotFoundException("No profile found for given user")
        val profileEntity = writeUserProfileDTO.toExistingEntity(userService.getUserByUserId(userId), existingProfile)
        val savedProfile = userProfileRepository.save(profileEntity)
        return savedProfile
    }
}