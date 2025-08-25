package com.example.userservice.exception

import com.example.userservice.dto.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.NoHandlerFoundException

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFound(ex: ResourceNotFoundException, request: WebRequest): ResponseEntity<ErrorResponse> {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.message, request)
    }

    // Access denied (Spring Security)
    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDenied(ex: AccessDeniedException, request: WebRequest): ResponseEntity<ErrorResponse> {
        return buildErrorResponse(HttpStatus.FORBIDDEN, ex.message, request)
    }

    // No handler found (invalid endpoint)
    @ExceptionHandler(NoHandlerFoundException::class)
    fun handleNotFound(ex: NoHandlerFoundException, request: WebRequest): ResponseEntity<ErrorResponse> {
        return buildErrorResponse(HttpStatus.NOT_FOUND, "Endpoint not found", request)
    }

    // Fallback for all other exceptions
    @ExceptionHandler(Exception::class)
    fun handleAll(ex: Exception, request: WebRequest): ResponseEntity<ErrorResponse> {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.message, request)
    }

    // Helper method
    private fun buildErrorResponse(status: HttpStatus, message: String?, request: WebRequest): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(
            status = status.value(),
            error = status.reasonPhrase,
            message = message ?: "Unexpected error",
            path = request.getDescription(false).replace("uri=", "")
        )
        return ResponseEntity(response, status)
    }
}
