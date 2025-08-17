package com.example.userservice.service.profile

import com.example.userservice.dto.ReadUserProfileDTO

interface UserProfileService {
    fun getUserProfileByUid(): ReadUserProfileDTO?

}
