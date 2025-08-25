package com.example.userservice.config

import com.example.userservice.dto.ErrorResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.access.AccessDeniedHandler
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@Component
class JwtAuthEntryPoint : AuthenticationEntryPoint {
    private val mapper = jacksonObjectMapper()

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        val errorResponse = ErrorResponse(
            timestamp = OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME),
            status = HttpServletResponse.SC_UNAUTHORIZED,
            error = "Unauthorized",
            message = authException.message,
            path = request.requestURI
        )

        response.contentType = "application/json"
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.writer.write(mapper.writeValueAsString(errorResponse))
    }
}

@Component
class CustomAccessDeniedHandler : AccessDeniedHandler {
    private val mapper = jacksonObjectMapper()

    override fun handle(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        accessDeniedException: AccessDeniedException?
    ) {
        val errorResponse = ErrorResponse(
                timestamp = OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME),
        status = HttpServletResponse.SC_FORBIDDEN,
        error = "Forbidden",
        message = accessDeniedException?.message,
        path = request?.requestURI ?: ""
        )

        response?.contentType = "application/json"
        response?.status = HttpServletResponse.SC_FORBIDDEN
        response?.writer?.write(mapper.writeValueAsString(errorResponse))
    }
}
