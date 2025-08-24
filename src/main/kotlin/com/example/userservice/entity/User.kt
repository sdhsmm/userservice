package com.example.userservice.entity

import com.example.userservice.service.auth.CurrentUser
import jakarta.persistence.*
import jakarta.validation.constraints.NotEmpty
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.time.Instant

@Entity
@Table(name = "users")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    val id: Long = 0,
    @Column(name = "user_email", nullable = false, unique = true)
    val email: String,
    @Column(name = "user_username", nullable = false, unique = true)
    val username: String, // userName =  userName ?: (firstName + @ + email)
    @Column(nullable = false)
    var passwordHash: String,
    var enabled: Boolean = true,
    var locked: Boolean = false,
    @Column(name = "orgid", nullable = false, unique = true, length = 10)
    var orgId: String = "", // Unique user code (6-char alphanumeric, uppercase)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = [JoinColumn(name = "user_id", nullable = false)],
        inverseJoinColumns = [JoinColumn(name = "role_id", nullable = false)],
    )
    @NotEmpty(message = "User must have at least one role")
    var roles: MutableSet<Role> = mutableSetOf(),
    @Column(nullable = false, updatable = false)
    val createdAt: Instant = Instant.now(),
    @Column(nullable = false)
    var updatedAt: Instant = Instant.now(),
    @OneToOne(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val profile: UserProfile? = null,
)

fun User.toCurrentUser(): CurrentUser =
    CurrentUser(
        id = this.id,
        orgId = this.orgId,
        email = this.email,
        username = this.username,
        passwordHash = this.passwordHash,
        authorities = this.roles.map { SimpleGrantedAuthority(it.name.toString()) },
        enabled = this.enabled,
        locked = this.locked,
    )
