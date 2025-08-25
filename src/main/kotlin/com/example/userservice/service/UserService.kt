package com.example.userservice.service

import com.example.userservice.dto.AuthResponse
import com.example.userservice.dto.LoginRequest
import com.example.userservice.dto.UserRegisterRequest
import com.example.userservice.entity.User
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity

interface UserService {
    fun createUser(userRegisterDto: UserRegisterRequest): User

    fun getUserByOrgId(orgId: String): User

    fun getUserByUserId(userId: Long): User
    fun getUserByEmail(email: String): User
}
