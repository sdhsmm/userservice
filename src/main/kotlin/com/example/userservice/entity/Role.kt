package com.example.userservice.entity

import com.example.userservice.enum.RoleType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "roles")
 class Role(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Enumerated(EnumType.STRING) // Store as string in DB
    @Column(nullable = false, unique = true)
    val name: RoleType, // e.g. ROLE_USER, ROLE_ADMIN
)
