package com.example.userservice.service.auth

interface TokenBlacklistService {
    fun blacklistToken(token: String)

    fun isTokenBlacklisted(token: String): Boolean
}