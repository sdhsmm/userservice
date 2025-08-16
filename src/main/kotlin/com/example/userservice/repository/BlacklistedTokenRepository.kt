package com.example.userservice.repository

import com.example.userservice.entity.BlacklistedToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.Instant

@Repository
interface BlacklistedTokenRepository : JpaRepository<BlacklistedToken, String> {
    fun existsByToken(token: String): Boolean
    fun deleteByExpiryDateBefore(now: Instant)
}
