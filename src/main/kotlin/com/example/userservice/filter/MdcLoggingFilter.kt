package com.example.userservice.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*

@Component
class MdcLoggingFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            // Attach a unique requestId for traceability
            MDC.put("requestId", UUID.randomUUID().toString())
            MDC.put("path", request.requestURI)

            filterChain.doFilter(request, response)
        } finally {
            MDC.clear() // Clean after request completes
        }
    }
}
