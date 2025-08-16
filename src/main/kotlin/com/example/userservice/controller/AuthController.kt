package com.example.userservice.controller

import com.example.userservice.dto.AuthResponse
import com.example.userservice.dto.LoginRequest
import com.example.userservice.dto.UserRegisterRequest
import com.example.userservice.service.UserService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/auth")
class AuthController (
    private val userService: UserService,
) {

    @PostMapping("/register",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun register(@RequestBody userRegisterDto: UserRegisterRequest) : ResponseEntity<String> {

                return userService.register(userRegisterDto)

    }


    @PostMapping("/login",
            consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun login(@RequestBody req: LoginRequest): ResponseEntity<AuthResponse> {
        return userService.login(req)
    }

    @PostMapping("/revoke")
    fun logout(request: HttpServletRequest): ResponseEntity<String> {
        return userService.logout(request)
    }

}