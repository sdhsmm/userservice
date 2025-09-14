package com.example.userservice.service.auth

import com.example.userservice.entity.toCurrentUser
import com.example.userservice.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository,
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user =
            userRepository.findByUsername(username)
                ?: userRepository.findByEmail(username) ?: throw UsernameNotFoundException("User not found with username: $username")
        return user.toCurrentUser()
    }
}
