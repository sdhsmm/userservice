package com.example.userservice.service.auth

import com.example.userservice.config.JwtUtils
import com.example.userservice.entity.BlacklistedToken
import com.example.userservice.repository.BlacklistedTokenRepository
import com.example.userservice.service.auth.TokenBlacklistService
import org.springframework.context.annotation.Primary
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.Instant

@Service
@Primary
class DatabaseTokenBlacklistService(
    private val repository: BlacklistedTokenRepository,
    private val jwtUtils: JwtUtils
): TokenBlacklistService {

    override fun blacklistToken(token: String) {
        if (!repository.existsByToken(token)) {
            val expiryDate = jwtUtils.extractExpiration(token).toInstant()
            repository.save(BlacklistedToken(token, expiryDate))
        }
    }

    override fun isTokenBlacklisted(token: String): Boolean {
        return repository.existsByToken(token)
    }

    @Scheduled(fixedRate = 3600000)
    fun cleanupExpiredTokens() {
        repository.deleteByExpiryDateBefore(Instant.now())
    }
}