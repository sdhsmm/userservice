package com.example.userservice.service.profile

import com.example.userservice.dto.ReadUserProfileDTO
import com.example.userservice.dto.WriteUserProfileDTO
import com.example.userservice.dto.toEntity
import com.example.userservice.entity.UserProfile
import com.example.userservice.entity.toReadUserProfileDTO
import com.example.userservice.exception.ResourceNotFoundException
import com.example.userservice.repository.UserProfileRepository
import com.example.userservice.repository.UserRepository
import com.example.userservice.service.UserService
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrElse

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
        val profileEntity = writeUserProfileDTO.toEntity(userService.getUserByUserId(userId))
        val savedProfile = userProfileRepository.save(profileEntity)
        return savedProfile
    }
}