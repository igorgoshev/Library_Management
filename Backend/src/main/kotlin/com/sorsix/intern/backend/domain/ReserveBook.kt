package com.sorsix.intern.backend.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne
import java.time.LocalDate

@Entity
class ReserveBook(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val dateFrom: LocalDate,
    val dateTo: LocalDate,
    @ManyToOne
    val customer: Customer,
    @ManyToOne
    val bookInLibrary: BookInLibrary,
) {
}