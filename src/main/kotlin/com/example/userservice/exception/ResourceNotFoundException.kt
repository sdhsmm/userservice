package com.example.userservice.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class ResourceNotFoundException(
    message: String,
) : RuntimeException(message)

@ResponseStatus(HttpStatus.BAD_REQUEST)
class IllegalArgumentException(
    message: String,
) : RuntimeException(message)
