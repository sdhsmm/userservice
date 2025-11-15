package com.example.userservice.controller

import com.example.userservice.dto.UserRegisterRequest
import com.example.userservice.dto.toWriteUserProfileDTO
import com.example.userservice.service.UserService
import com.example.userservice.service.profile.UserProfileService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/user")
class UserController(
    private val userService: UserService,
    private val userProfileService: UserProfileService
) {
    @PostMapping(
        "/register",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun register(
        @RequestBody userRegisterDto: UserRegisterRequest,
    ): ResponseEntity<String> {
        val registeredUser = userService.createUser(userRegisterDto)
        userProfileService.createUserProfile(registeredUser.id, userRegisterDto.toWriteUserProfileDTO())
        return ResponseEntity.ok("User ${registeredUser.email} registered successfully")
    }

}

