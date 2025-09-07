package com.example.userservice.service.auth

import com.example.auth_api.TokenBlacklistService
import com.example.userservice.config.JwtUtils
import com.example.userservice.dto.AuthResponse
import com.example.userservice.dto.LoginRequest
import com.example.userservice.dto.UserRegisterRequest
import com.example.userservice.dto.toWriteUserProfileDTO
import com.example.userservice.entity.User
import com.example.userservice.service.UserService
import com.example.userservice.service.profile.UserProfileService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthServiceImpl(
    private val userService: UserService,
    private val userProfileService: UserProfileService,
    private val tokenBlacklistService: TokenBlacklistService,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtils: JwtUtils,
    @Value("\${app.jwt.expiration-ms}") private val expMs: Long
): AuthService {

    @Transactional
    override fun register(userRegisterDto: UserRegisterRequest): User {
        val registeredUser = userService.createUser(userRegisterDto)
        userProfileService.createUserProfile(registeredUser.id, userRegisterDto.toWriteUserProfileDTO())
        return registeredUser
    }

    override fun login(req: LoginRequest): ResponseEntity<AuthResponse> {
       val employee =
           userService.getUserByEmail(req.email)
       val match = passwordEncoder.matches(req.password, employee.passwordHash)
       return if (match) {
           val token = jwtUtils.generateAccessToken(employee)
           ResponseEntity.ok(AuthResponse(accessToken = token, expiresIn = expMs))
       } else {
           ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
       }
    }

    override fun logout(request: HttpServletRequest): ResponseEntity<String> {
        val authHeader =
            request.getHeader("Authorization")
                ?: return ResponseEntity.badRequest().body("No token provided")

        if (authHeader.startsWith("Bearer ")) {
            val token = authHeader.substring(7)
            tokenBlacklistService.blacklistToken(token)
            return ResponseEntity.ok("Logged out successfully")
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorised.")
    }
}