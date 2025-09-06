package com.example.userservice.controller

import com.example.userservice.dto.AuthResponse
import com.example.userservice.dto.LoginRequest
import com.example.userservice.dto.UserRegisterRequest
import com.example.userservice.dto.toWriteUserProfileDTO
import com.example.userservice.service.UserService
import com.example.userservice.service.auth.AuthService
import com.example.userservice.service.profile.UserProfileService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping(
        "/register",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun register(
        @RequestBody userRegisterDto: UserRegisterRequest,
    ): ResponseEntity<String> {
        val registeredUser = authService.register(userRegisterDto)
        return ResponseEntity.ok("User ${registeredUser.email} registered successfully")
    }

    @PostMapping(
        "/login",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun login(
        @RequestBody req: LoginRequest,
    ): ResponseEntity<AuthResponse> = authService.login(req)

    @PostMapping("/revoke")
    fun logout(request: HttpServletRequest): ResponseEntity<String> = authService.logout(request)
}
