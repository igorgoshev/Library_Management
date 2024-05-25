package com.sorsix.intern.backend.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany

@Entity
class CustomerBook(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    @ManyToOne
    var customer: Customer,
    @ManyToOne
    var book: Book,
    @OneToMany(mappedBy = "customerBook")
    var trades: MutableList<Trade> = mutableListOf(),
    var available: Boolean = true
) {
}