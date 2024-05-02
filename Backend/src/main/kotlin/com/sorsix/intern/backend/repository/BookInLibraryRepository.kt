package com.sorsix.intern.backend.repository

import com.sorsix.intern.backend.domain.BookInLibrary
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookInLibraryRepository : JpaRepository<BookInLibrary, Long> {
    fun findAllByIdIn(bookInLibrary: List<Long>) : MutableList<BookInLibrary>
}