package com.example.userservice.service

import com.example.userservice.entity.Role
import com.example.userservice.enum.RoleType
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class RoleServiceImpl(
    private val appCache: AppCache
): RoleService {

    override fun getRole(roleType: RoleType): Role? {
        return appCache.roles[roleType]
    }
}
