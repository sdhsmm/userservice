package com.example.userservice.service.auth

import com.example.userservice.config.JwtUtils
import com.example.userservice.service.auth.TokenBlacklistService
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
//@Primary
class InMemoryTokenBlacklistService (
    private val jwtUtil: JwtUtils
) : TokenBlacklistService {
    private val blacklist = ConcurrentHashMap<String, Long>()

    override fun blacklistToken(token: String) {
        val expiration = jwtUtil.extractExpiration(token)
        blacklist[token] = expiration.time
    }

    override fun isTokenBlacklisted(token: String): Boolean {
        val expiry = blacklist[token] ?: return false
        // Clean up if expired
        if (System.currentTimeMillis() > expiry) {
            blacklist.remove(token)
            return false
        }
        return true
    }
}