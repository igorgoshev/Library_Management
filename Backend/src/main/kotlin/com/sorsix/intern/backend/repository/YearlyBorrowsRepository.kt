package com.sorsix.intern.backend.repository

import com.sorsix.intern.backend.domain.views.YearlyBorrows
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface YearlyBorrowsRepository : JpaRepository<YearlyBorrows, Long> {
    @Transactional
    @Modifying
    @Query("refresh materialized view borrows_thorugh_months", nativeQuery = true)
    fun refreshView()
}