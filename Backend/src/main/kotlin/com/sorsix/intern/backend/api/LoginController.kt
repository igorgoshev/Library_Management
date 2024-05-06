package com.sorsix.intern.backend.api

import com.sorsix.intern.backend.domain.User
import com.sorsix.intern.backend.domain.dto.LoginRequest
import com.sorsix.intern.backend.domain.dto.LoginResponse
import com.sorsix.intern.backend.repository.UserRepository
import com.sorsix.intern.backend.service.impl.LoginService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class LoginController {
    @Autowired
    private val userRepository: UserRepository? = null
    @Autowired
    private val loginService: LoginService? = null
    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest?): ResponseEntity<LoginResponse> {
        return ResponseEntity.ok(loginRequest?.let { loginService?.login(it) })
    }

    @PostMapping("/register")
    fun register(): ResponseEntity<*> {
        val u: User = User(
            email = "darsov2@gmail.com",
            password = passwordEncoder.encode("orel"),
            name = "David Arsov",
            address = "Negde"
        )
        userRepository?.save(u)
        return ResponseEntity.ok().build<Any>()
    }
}