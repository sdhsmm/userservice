package com.example.userservice.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.Instant

@Entity
data class BlacklistedToken(
    @Id
    val token: String,            // Store the whole JWT
    val expiryDate: Instant       // Store token's expiry so we can auto-clean later
)
