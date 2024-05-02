package com.sorsix.intern.backend.repository

import com.sorsix.intern.backend.domain.Librarian
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LibrarianRepository : JpaRepository<Librarian, Long> {
}