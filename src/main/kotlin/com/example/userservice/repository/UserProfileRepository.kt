package com.example.userservice.repository

import com.example.userservice.entity.UserProfile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserProfileRepository : JpaRepository<UserProfile, Long> {
    fun findByUserId(userId: Long): UserProfile?
}
