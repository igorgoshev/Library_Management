package com.sorsix.intern.backend.repository

import com.sorsix.intern.backend.domain.Author
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthorRepository : JpaRepository<Author, Long> {
    fun findAllByIdIn(ids: List<Long>) : MutableList<Author>
}