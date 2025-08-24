package com.example.userservice.service

import com.example.userservice.config.JwtUtils
import com.example.userservice.dto.AuthResponse
import com.example.userservice.dto.LoginRequest
import com.example.userservice.dto.UserRegisterRequest
import com.example.userservice.dto.toWriteUserProfileDTO
import com.example.userservice.entity.OrgIdSequence
import com.example.userservice.entity.User
import com.example.userservice.enum.RoleType
import com.example.userservice.exception.ResourceNotFoundException
import com.example.userservice.repository.OrgIdSequenceRepository
import com.example.userservice.repository.UserRepository
import com.example.userservice.service.auth.TokenBlacklistService
import com.example.userservice.service.profile.UserProfileService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrElse

@Service
class UserServiceImpl(
    private val roleService: RoleService,
    private val orgIdGenerator: OrgIdGenerator,
    private val userRepository: UserRepository,
    private val orgIdSequenceRepository: OrgIdSequenceRepository,
    private val passwordEncoder: PasswordEncoder,
) : UserService {
    @Transactional
    override fun createUser(userRegisterDto: UserRegisterRequest): User {
        if (userRepository.findByEmail(userRegisterDto.email) != null) {
            throw IllegalStateException("Email already exists")
        }
        val roles =
            mutableSetOf(
                roleService.getRole(RoleType.ROLE_USER)
                    ?: throw IllegalStateException("ROLE_USER not found"),
            )

        val seq = orgIdSequenceRepository.save(OrgIdSequence()).id
        val orgId = orgIdGenerator.generateOrgId(seq)

        val user =
            User(
                username = userRegisterDto.username ?: ("username" + "@" + userRegisterDto.email),
                email = userRegisterDto.email,
                passwordHash = passwordEncoder.encode(userRegisterDto.password),
                orgId = orgId,
                roles = roles,
            )
        val savedUser = userRepository.save(user)
        return savedUser
    }

    override fun getUserByOrgId(orgId: String): User {
        return userRepository.findByOrgId(orgId) ?: throw ResourceNotFoundException("OrgId: $orgId, No User found.")
    }

    override fun getUserByUserId(userId: Long): User {
        return userRepository.findById(userId).getOrElse { throw ResourceNotFoundException("No User found.")}
    }

    override fun getUserByEmail(email: String): User {
        return userRepository.findByEmail(email) ?: throw ResourceNotFoundException("No User found.")
    }
}
