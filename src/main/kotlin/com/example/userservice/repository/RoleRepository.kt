package com.example.userservice.repository

import com.example.userservice.entity.Role
import com.example.userservice.enum.RoleType
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<Role, Long> {
    fun existsByName(name: RoleType): Boolean

    fun findByName(name: RoleType): Role?
}
