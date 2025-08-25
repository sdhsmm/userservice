package com.example.userservice.service

import com.example.userservice.entity.Role
import com.example.userservice.enum.RoleType
import org.springframework.stereotype.Component

@Component
class AppCache {
    val roles: MutableMap<RoleType, Role> = mutableMapOf()
}
