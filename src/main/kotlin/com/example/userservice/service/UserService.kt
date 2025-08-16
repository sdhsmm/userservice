package com.example.userservice.service

import com.example.userservice.dto.AuthResponse
import com.example.userservice.dto.LoginRequest
import com.example.userservice.dto.UserRegisterRequest
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity

interface UserService {
    fun register(userRegisterDto: UserRegisterRequest): ResponseEntity<String>
    fun login(req: LoginRequest): ResponseEntity<AuthResponse>
    fun logout(request: HttpServletRequest): ResponseEntity<String>
}