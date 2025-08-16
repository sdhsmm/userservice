package com.example.userservice.service

import com.example.userservice.entity.Role
import com.example.userservice.enum.RoleType
import java.util.Optional

interface RoleService {
    fun getRole(roleType: RoleType): Role?
}