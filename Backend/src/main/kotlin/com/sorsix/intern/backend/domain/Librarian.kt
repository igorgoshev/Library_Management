package com.sorsix.intern.backend.domain

import jakarta.persistence.Entity
import jakarta.persistence.OneToMany
import jakarta.persistence.PrimaryKeyJoinColumn

@Entity
@PrimaryKeyJoinColumn
class Librarian(
    @OneToMany(mappedBy = "librarian")
    val borrowedBooks: List<BorrowBook>
) : User() {
}