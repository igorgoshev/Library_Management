package com.sorsix.intern.backend.api

import com.sorsix.intern.backend.config.CurrentUser
import com.sorsix.intern.backend.domain.dto.UserResponse
import com.sorsix.intern.backend.security.UserPrincipal
import com.sorsix.intern.backend.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController {
    @Autowired
    private val userService: UserService? = null

    @GetMapping("/me")
    fun getCurrentUser(@CurrentUser userPrincipal: UserPrincipal): ResponseEntity<UserResponse> {
        return ResponseEntity.ok(userService?.getUserInfoById((userPrincipal.id)))
    }
}