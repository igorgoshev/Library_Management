package com.sorsix.intern.backend.domain

import jakarta.persistence.*
import java.time.LocalDate

@Entity
class Trade(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    @ManyToOne
    var customer: Customer,

    @ManyToOne
    var customerBook: CustomerBook,

    var dateFrom: LocalDate,
    var dateTo: LocalDate?,
) {
}