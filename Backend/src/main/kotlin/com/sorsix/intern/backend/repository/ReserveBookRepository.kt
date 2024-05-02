package com.sorsix.intern.backend.repository

import com.sorsix.intern.backend.domain.ReserveBook
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReserveBookRepository : JpaRepository<ReserveBook, Long> {
    fun findAllByIdIn(reservedBooks: List<Long>) : MutableList<ReserveBook>
}