package com.sorsix.intern.backend.repository

import com.sorsix.intern.backend.domain.views.YearlyReservations
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface YearlyReservationsRepository : JpaRepository<YearlyReservations, Long> {
    @Transactional
    @Modifying
    @Query("refresh materialized view reservations_thorugh_months", nativeQuery = true)
    fun refreshView()
}
