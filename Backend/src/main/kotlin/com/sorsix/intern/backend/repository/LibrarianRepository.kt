package com.sorsix.intern.backend.repository

import com.sorsix.intern.backend.domain.Librarian
import com.sorsix.intern.backend.domain.Library
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface LibrarianRepository : JpaRepository<Librarian, Long> {
    @Query("select l.libraryStore.id from Librarian l where l.id = :id")
    fun findStoreIdByUserId(@Param("id") id: Long): Long?
    @Query("select l.libraryStore.library from Librarian l where l.id = :id")
    fun findLibraryByUserId(@Param("id") id: Long): Library?
}