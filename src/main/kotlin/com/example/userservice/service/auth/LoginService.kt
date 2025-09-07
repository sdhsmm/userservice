package com.example.userservice.service.auth

import com.example.userservice.dto.AuthResponse

interface LoginService {
    fun login(username: String, password: String): AuthResponse
}
