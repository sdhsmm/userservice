package com.example.userservice.service

import com.example.userservice.entity.Role
import com.example.userservice.enum.RoleType
import com.example.userservice.repository.RoleRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class AppCacheInitializer(
    private val roleRepository: RoleRepository,
    private val appCache: AppCache,
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        // Roles
        RoleType.entries.forEach { roleType ->
            val role =
                roleRepository.findByName(roleType)
                    ?: roleRepository.save(Role(name = roleType))
            appCache.roles[roleType] = role
        }
    }
}
