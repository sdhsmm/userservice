package com.example.userservice.config

import com.example.userservice.entity.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.Instant
import java.util.Date

@Component
class JwtUtils(
    @Value("\${app.jwt.secret}") private val secret: String,
    @Value("\${app.jwt.expiration-ms}") private val expMs: Long,
) {
    fun accessTtlSeconds() = expMs / 1000

    fun generateAccessToken(user: User): String {
        val now = Date()
        val expiry = Date(now.time + expMs)
        val roles = user.roles.map { it.name }
        val key = Keys.hmacShaKeyFor(secret.toByteArray())
        return Jwts
            .builder()
            .setSubject(user.username)
            .claim("roles", roles)
            .claim("orgid", user.orgId)
            .setIssuedAt(now)
            .setExpiration(expiry)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    fun generateRefreshToken(user: User): Pair<String, Instant> {
        val ttl = Duration.ofDays(7)
        val now = Date()
        val roles = user.roles.map { it.name }
        val expiry = Date(now.time + ttl.toMillis())
        val token =
            Jwts
                .builder()
                .setSubject(user.username)
                .claim("roles", roles)
                .claim("orgid", user.orgId)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(Keys.hmacShaKeyFor(secret.toByteArray()), SignatureAlgorithm.HS256)
                .compact()
        return token to expiry.toInstant()
    }

    fun parseClaims(jwt: String): Claims =
        Jwts
            .parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(secret.toByteArray()))
            .build()
            .parseClaimsJws(jwt)
            .body

    fun extractUsername(token: String): String = extractClaim(token, Claims::getSubject)

    fun <T> extractClaim(
        token: String,
        claimsResolver: (Claims) -> T,
    ): T {
        val claims = parseClaims(token)
        return claimsResolver(claims)
    }

    fun validateToken(
        token: String,
    ): Boolean {
        return (!isTokenExpired(token))
    }

    private fun isTokenExpired(token: String): Boolean = extractExpiration(token).before(Date())

    fun extractExpiration(token: String): Date = extractClaim(token, Claims::getExpiration)
}
