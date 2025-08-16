package com.example.userservice.config

import com.example.userservice.entity.Role
import com.example.userservice.enum.RoleType
import com.example.userservice.repository.RoleRepository
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component

@Component
class RoleSeeder(private val roleRepository: RoleRepository) {

    @PostConstruct
    fun initRoles() {
        val defaultRoles = RoleType.entries

        defaultRoles.forEach { roleType ->
            if (roleRepository.findByName(roleType) == null) {
                roleRepository.save(Role(name = roleType))
            }
        }
    }
}
