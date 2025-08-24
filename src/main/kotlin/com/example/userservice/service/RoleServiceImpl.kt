package com.example.userservice.service

import com.example.userservice.entity.Role
import com.example.userservice.enum.RoleType
import org.springframework.stereotype.Service

@Service
class RoleServiceImpl(
    private val appCache: AppCache,
) : RoleService {
    override fun getRole(roleType: RoleType): Role? = appCache.roles[roleType]
}
