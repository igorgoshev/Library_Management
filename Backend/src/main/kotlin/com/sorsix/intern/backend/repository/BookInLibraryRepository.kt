package com.sorsix.intern.backend.repository

import com.sorsix.intern.backend.domain.BookInLibrary
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface BookInLibraryRepository : JpaRepository<BookInLibrary, Long> {
    fun findAllByIdIn(bookInLibrary: List<Long>) : MutableList<BookInLibrary>
    fun findAllByBookId(bookId: Long) : MutableList<BookInLibrary>
    fun findAllByLibraryStore_Id(id: Long) : List<BookInLibrary>

    fun findAllByBookIdAndIsReservedFalseAndIsLentFalse(bookId: Long) : MutableList<BookInLibrary>
    fun findAllByBookNameStartsWithAndIsLentFalseAndIsReservedFalse(bookNameStartsWith: String) : List<BookInLibrary>
    fun findAllByBookNameIgnoreCaseContaining(query: String) : List<BookInLibrary>

    fun findFirstByLibraryStore_IdAndBook_Id (libraryStore_id: Long, book_id: Long): BookInLibrary
    fun findAllByLibraryStore_IdAndBook_Id (libraryStore_id: Long, book_id: Long): List<BookInLibrary>
    @Query("SELECT (SELECT COUNT(DISTINCT book_id) * 100 / (SELECT COUNT(*) FROM book) FROM book_in_library WHERE library_store_id = :id);\n", nativeQuery = true)
    fun getBooksRatio(@Param("id") id: Long): Double
}