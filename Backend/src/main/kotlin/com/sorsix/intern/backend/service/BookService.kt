package com.sorsix.intern.backend.service

import com.sorsix.intern.backend.api.dtos.*
import com.sorsix.intern.backend.domain.*
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate

interface BookService {
    fun findAll() : List<Book>
    fun findById(id: Long) : Book?
    fun save(id: Long?, name: String, publishedYear: LocalDate, imgUrl: String, publishingHouseId: Long, authorsId: List<Long>,
             bookInLibraryId: List<Long>, categoriesId: List<Long>, wishListsId: List<Long>, reviewsId: List<Long>,
             customerBooksId: List<Long>, description: String) : Book?
    fun delete(id: Long) : Book?

    fun getBooksContaining(query: String, category: String) : Map<Char?, List<BookCard>>
    fun getBooksByCategory(category: String) : Map<Char, List<BookCard>>

    fun getBooksContainingAdmin(query: String) : Map<Char?, List<AvailableBooks>>
    fun findAllByIdContaining(booksId: List<Long>): MutableList<Book>
    fun findAllBooksForTable(): List<BookInTable>
    fun findAllByLetter(letter: String): List<BookInTable>
    fun findBookCardsByLetters(): Map<Char, List<BookCard>>
    fun findBookCardsByLetter(letter: Char): Map<Char, List<BookCard>>
    fun getBookDetailsById(id: Long): BookInTable?
    fun addBook(book: AddBook, file: MultipartFile): Book
    fun getBookAvailability(id: Long): List<BookAvailability>?
    fun findAvailableBooksByLetter(letter: Char?): Map<Char, List<AvailableBooks>>
    fun findAllAvailableBooksByLetter(letter: Char?): Map<Char, List<BookCard>>
    fun lendBook(userId: Long, bookId: Long)
    fun getPopularBooks(): List<BookCard>
    fun getBookCopies(bookId: Long, userId: Long): List<AvailableBook>
    fun getCustomerBooks(userId: Long): List<CustomerBookCard>
    fun getAllTradesByCustomerBook(bookId: Long): List<LentBookDetails>
    fun addBookToCustomer(userId: Long, bookId: Long)
}