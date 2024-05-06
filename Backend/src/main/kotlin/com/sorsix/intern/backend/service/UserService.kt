package com.sorsix.intern.backend.service

import com.sorsix.intern.backend.domain.dto.UserResponse

interface UserService {
    fun getUserInfoById(id: Long): UserResponse
}