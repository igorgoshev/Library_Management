package com.sorsix.intern.backend.repository

import com.sorsix.intern.backend.domain.views.LoansInLastDays
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface LoansInLastDaysRepository : JpaRepository<LoansInLastDays, Long> {
    @Transactional
    @Modifying
    @Query("refresh materialized view loans_in_last_days", nativeQuery = true)
    fun refreshView()
}