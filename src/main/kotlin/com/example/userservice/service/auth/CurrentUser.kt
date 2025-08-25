package com.example.userservice.service.auth

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

class CurrentUser(
    val id: Long,
    val orgId: String,
    val email: String,
    username: String,
    passwordHash: String,
    authorities: Collection<GrantedAuthority>,
    enabled: Boolean = true,
    accountNonExpired: Boolean = true,
    credentialsNonExpired: Boolean = true,
    locked: Boolean = false,
) : User(username, passwordHash, enabled, accountNonExpired, credentialsNonExpired, !locked, authorities)
