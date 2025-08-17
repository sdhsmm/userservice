package com.example.userservice.repository

import com.example.userservice.entity.UserProfile
import org.springframework.data.jpa.repository.JpaRepository

interface UserProfileRepository : JpaRepository<UserProfile, Long> {
}