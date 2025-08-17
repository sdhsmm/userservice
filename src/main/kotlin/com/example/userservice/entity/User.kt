package com.example.userservice.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotEmpty
import java.time.Instant

@Entity @Table(name = "users")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(nullable = false, unique = true)
    val email: String,
    @Column(nullable = false, unique = true)
    //userName =  userName ?. (firstName + @ + email)
    val username: String,
    @Column(nullable = false)
    var passwordHash: String,
    var firstName: String? = null,
    var lastName: String? = null,
    var enabled: Boolean = true,
    var locked: Boolean = false,

    // New: unique user code (6-char alphanumeric, uppercase)
    @Column(name = "orgid", nullable = false, unique = true, length = 10)
    var orgId: String = "",

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = [JoinColumn(name = "user_id", nullable = false)],
        inverseJoinColumns = [JoinColumn(name = "role_id", nullable = false)]
    )
    @NotEmpty(message = "User must have at least one role")
    var roles: MutableSet<Role> = mutableSetOf(),

    @Column(nullable = false, updatable = false)
    val createdAt: Instant = Instant.now(),
    @Column(nullable = false)
    var updatedAt: Instant = Instant.now()
)



@Entity @Table(name = "refresh_tokens")
data class RefreshToken(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id")
    val user: User,
    @Column(nullable = false, unique = true)
    val token: String,
    @Column(nullable = false)
    val expiresAt: Instant,
    @Column(nullable = false)
    var revoked: Boolean = false
)