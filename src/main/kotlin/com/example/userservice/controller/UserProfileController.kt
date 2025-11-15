package com.example.userservice.controller

import com.example.userservice.dto.ReadUserProfileDTO
import com.example.userservice.dto.WriteUserProfileDTO
import com.example.userservice.entity.toReadUserProfileDTO
import com.example.userservice.service.auth.getLoggedInUser
import com.example.userservice.service.profile.UserProfileService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/profile")
class UserProfileController(
    private val userProfileService: UserProfileService,
) {
    @GetMapping
    fun getCurrentUserProfile(): ResponseEntity<ReadUserProfileDTO> {
        val currentUser = getLoggedInUser()
        val profile = userProfileService.getUserProfileByUserId(currentUser.id)
        return ResponseEntity.status(200).body(profile.toReadUserProfileDTO())
    }

    @GetMapping("/orgid")
    fun getUserProfile(@PathVariable orgId: String): ResponseEntity<ReadUserProfileDTO> {
        val profile = userProfileService.getUserProfileByOrgId(orgId)
        return ResponseEntity.status(200).body(profile.toReadUserProfileDTO())
    }

    @PostMapping
    fun createUserProfile(@RequestBody writeUserProfileDTO: WriteUserProfileDTO): ResponseEntity<ReadUserProfileDTO> {
        val currentUser = getLoggedInUser()
        val profile = userProfileService.createUserProfile(currentUser.id, writeUserProfileDTO)
        return ResponseEntity.status(200).body(profile.toReadUserProfileDTO())
    }

    @PutMapping
    fun updateUserProfile(@RequestBody writeUserProfileDTO: WriteUserProfileDTO): ResponseEntity<ReadUserProfileDTO> {
        val currentUser = getLoggedInUser()
        val profile = userProfileService.updateUserProfile(currentUser.id, writeUserProfileDTO)
        return ResponseEntity.status(200).body(profile.toReadUserProfileDTO())
    }

}
