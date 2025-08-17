package com.example.userservice.service.profile

import com.example.userservice.dto.ReadUserProfileDTO
import com.example.userservice.repository.UserProfileRepository
import com.example.userservice.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserProfileServiceImpl(
    userProfileRepository: UserProfileRepository
): UserProfileService {
    override fun getUserProfileByUid(): ReadUserProfileDTO? {
        return null
    }
}