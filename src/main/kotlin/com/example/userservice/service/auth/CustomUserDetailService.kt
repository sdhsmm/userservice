package com.example.userservice.service.auth

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import com.example.userservice.repository.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import kotlin.jvm.optionals.getOrElse
import org.springframework.security.core.userdetails.User as SecurityUser

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User not found with username: $username")

        return SecurityUser(
            user.username,
            user.passwordHash,
            user.roles.map { SimpleGrantedAuthority(it.name.toString()) }
        )
    }
}