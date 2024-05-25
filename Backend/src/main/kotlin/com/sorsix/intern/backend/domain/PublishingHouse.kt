package com.sorsix.intern.backend.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity
class PublishingHouse(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    var name: String = "",
    var address: String = "",
    @JsonIgnore
    @OneToMany(mappedBy = "publishingHouse")
    var books: MutableList<Book> = mutableListOf<Book>()
) {
}