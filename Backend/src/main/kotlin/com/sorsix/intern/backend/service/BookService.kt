package com.sorsix.intern.backend.service

import com.sorsix.intern.backend.api.dtos.BookCard
import com.sorsix.intern.backend.api.dtos.BookInTable
import com.sorsix.intern.backend.domain.*
import java.time.LocalDate

interface BookService {
    fun findAll() : List<Book>
    fun findById(id: Long) : Book?
    fun save(id: Long?, name: String, publishedYear: LocalDate, imgUrl: String, publishingHouseId: Long, authorsId: List<Long>,
             bookInLibraryId: List<Long>, categoriesId: List<Long>, wishListsId: List<Long>, reviewsId: List<Long>,
             customerBooksId: List<Long>, description: String) : Book?
    fun delete(id: Long) : Book?
    fun findAllByIdContaining(booksId: List<Long>): MutableList<Book>
    fun findAllBooksForTable(): List<BookInTable>
    fun findBookCardsByLetters(): Map<Char, List<BookCard>>
    fun getBookDetailsById(id: Long): BookInTable?
}