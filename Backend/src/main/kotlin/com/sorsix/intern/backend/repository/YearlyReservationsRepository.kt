package com.sorsix.intern.backend.repository

import com.sorsix.intern.backend.domain.views.YearlyReservations
import org.springframework.data.jpa.repository.JpaRepository

interface YearlyReservationsRepository : JpaRepository<YearlyReservations, Long> {
}