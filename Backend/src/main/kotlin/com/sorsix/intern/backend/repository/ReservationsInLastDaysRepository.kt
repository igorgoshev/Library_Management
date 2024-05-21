package com.sorsix.intern.backend.repository

import com.sorsix.intern.backend.domain.views.LoansInLastDays
import com.sorsix.intern.backend.domain.views.ReservationsInLastDays
import org.springframework.data.jpa.repository.JpaRepository

interface ReservationsInLastDaysRepository : JpaRepository<ReservationsInLastDays, Long> {
}