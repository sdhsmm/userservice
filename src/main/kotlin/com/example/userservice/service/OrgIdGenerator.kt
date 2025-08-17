package com.example.userservice.service

import org.springframework.stereotype.Service
import java.util.concurrent.locks.ReentrantLock

@Service
class OrgIdGenerator {

    private val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    private val lock = ReentrantLock()

    fun generateOrgId(sequence: Long): String {
        val numericPart = (sequence % 10_000).toInt() // 0000 → 9999
        val alphaIndex = (sequence / 10_000).toInt()

        val c1 = alphabet[(alphaIndex / (26 * 26)) % 26]
        val c2 = alphabet[(alphaIndex / 26) % 26]
        val c3 = alphabet[alphaIndex % 26]

        return "$c1$c2$c3${numericPart.toString().padStart(4, '0')}"
    }
}
