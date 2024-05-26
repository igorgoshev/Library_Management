package com.sorsix.intern.backend.repository

import com.sorsix.intern.backend.domain.Customer
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository : JpaRepository<Customer, Long> {
    @Modifying
    @Transactional
    @Query("insert into customer(id) values(:id)", nativeQuery = true)
    fun makeCustomer(id: Long): Unit
}
