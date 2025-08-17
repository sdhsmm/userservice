package com.example.userservice.controller

import com.example.userservice.dto.ReadUserProfileDTO
import com.example.userservice.service.profile.UserProfileService
import jakarta.websocket.server.PathParam
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/profile")
class UserProfileController(
    private val userProfileService: UserProfileService
) {

    @GetMapping( "/{uid}")
    fun getUserProfile(@PathVariable("uid") uin: Long): ResponseEntity<ReadUserProfileDTO>{
        val profile = userProfileService.getUserProfileByUid();
        return ResponseEntity.status(200).body(profile)
    }

}