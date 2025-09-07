package com.example.userservice.service.auth

import com.example.auth_api.TokenBlacklistService
import com.example.userservice.config.JwtUtils
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class RedisTokenBlacklistService(
    private val redisTemplate: StringRedisTemplate,
    private val jwtUtil: JwtUtils,
) : TokenBlacklistService {
    override fun blacklistToken(token: String) {
        val expiration = jwtUtil.extractExpiration(token)
        val ttl = expiration.time - System.currentTimeMillis()
        redisTemplate.opsForValue().set(token, "blacklisted", ttl, TimeUnit.MILLISECONDS)
    }

    override fun isTokenBlacklisted(token: String): Boolean = redisTemplate.hasKey(token)
}
