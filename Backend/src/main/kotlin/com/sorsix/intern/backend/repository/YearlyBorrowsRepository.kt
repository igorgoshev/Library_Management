package com.sorsix.intern.backend.repository

import com.sorsix.intern.backend.domain.views.YearlyBorrows
import org.springframework.data.jpa.repository.JpaRepository

interface YearlyBorrowsRepository : JpaRepository<YearlyBorrows, Long> {
}