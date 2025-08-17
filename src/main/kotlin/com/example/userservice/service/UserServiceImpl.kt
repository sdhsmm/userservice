package com.example.userservice.service

import com.example.userservice.config.JwtUtils
import com.example.userservice.dto.AuthResponse
import com.example.userservice.dto.LoginRequest
import com.example.userservice.dto.UserRegisterRequest
import com.example.userservice.entity.OrgIdSequence
import com.example.userservice.entity.User
import com.example.userservice.enum.RoleType
import com.example.userservice.repository.OrgIdSequenceRepository
import com.example.userservice.repository.UserRepository
import com.example.userservice.service.auth.TokenBlacklistService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val roleService: RoleService,
    private val orgIdGenerator: OrgIdGenerator,
    private val userRepository: UserRepository,
    private val orgIdSequenceRepository: OrgIdSequenceRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtil: JwtUtils,
    private val tokenBlacklistService: TokenBlacklistService,
    @Value("\${app.jwt.expiration-ms}") private val expMs: Long
): UserService {

    @Transactional
    override fun register(userRegisterDto: UserRegisterRequest): ResponseEntity<String> {
        if (userRepository.findByEmail(userRegisterDto.email) != null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists")
        }
        val roles = mutableSetOf(roleService.getRole(RoleType.ROLE_USER)
                ?: throw IllegalStateException("ROLE_USER not found"))

        val seq = orgIdSequenceRepository.save(OrgIdSequence()).id
        val orgId = orgIdGenerator.generateOrgId(seq)

        val user = User(
            username = userRegisterDto.username?: (userRegisterDto.firstName + "@" + userRegisterDto.email),
            email = userRegisterDto.email,
            firstName = userRegisterDto.firstName,
            lastName = userRegisterDto.lastName,
            passwordHash = passwordEncoder.encode(userRegisterDto.password),
            orgId = orgId,
            roles = roles
        )
        userRepository.save(user)
        return ResponseEntity.ok("User registered successfully")

    }

    override fun login(req: LoginRequest): ResponseEntity<AuthResponse> {
        val employee = userRepository.findByEmail(req.email)
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        val match = passwordEncoder.matches(req.password, employee.passwordHash)
        return if (match) {
            val token = jwtUtil.generateAccessToken(employee)
            ResponseEntity.ok(AuthResponse(accessToken = token, expiresIn = expMs))
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }

    override fun logout(request: HttpServletRequest): ResponseEntity<String> {
        val authHeader = request.getHeader("Authorization")
            ?: return ResponseEntity.badRequest().body("No token provided")

        if (authHeader.startsWith("Bearer ")) {
            val token = authHeader.substring(7)
            tokenBlacklistService.blacklistToken(token)
            return ResponseEntity.ok("Logged out successfully")
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorised.")
    }

}