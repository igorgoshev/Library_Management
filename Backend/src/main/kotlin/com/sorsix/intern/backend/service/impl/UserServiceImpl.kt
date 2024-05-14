package com.sorsix.intern.backend.service.impl

import com.sorsix.intern.backend.domain.Customer
import com.sorsix.intern.backend.domain.User
import com.sorsix.intern.backend.domain.dto.UserResponse
import com.sorsix.intern.backend.repository.CustomerRepository
import com.sorsix.intern.backend.repository.UserRepository
import com.sorsix.intern.backend.service.UserMapper
import com.sorsix.intern.backend.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    @Autowired
    private val userRepository: UserRepository,
    private val customerRepository: CustomerRepository
) : UserService {
    override fun getUserInfoById(id: Long): UserResponse {
        println("Getting user info by id: $id")

        val user: User = userRepository
            .findById(id)
            .orElseThrow { RuntimeException("User not found with ID: %s.".formatted(id)) }

        return UserMapper.mapToUserResponse(user)
    }

    override fun findById(id: Long): Customer? = customerRepository.findByIdOrNull(id)


}