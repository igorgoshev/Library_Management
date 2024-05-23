package com.sorsix.intern.backend.repository

import com.sorsix.intern.backend.domain.Book
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface BookRepository : JpaRepository<Book, Long> {
    fun findAllByIdIn(booksId: List<Long>): MutableList<Book>
    fun findAllByNameStartsWith(nameStartsWith: String): List<Book>
    @Query("select b from Book b join PopularBook pb ON b.id = pb.id")
    fun findPopularBookDetails(): List<Book>
    @Transactional
    @Modifying
    @Query("refresh materialized view popular_books", nativeQuery = true)
    fun refreshView()

    fun findAllByNameIgnoreCaseContaining(query: String): List<Book>
}