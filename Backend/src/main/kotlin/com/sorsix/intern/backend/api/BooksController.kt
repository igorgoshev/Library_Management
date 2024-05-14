package com.sorsix.intern.backend.api

import com.sorsix.intern.backend.api.dtos.AddBook
import com.sorsix.intern.backend.api.dtos.AddReview
import com.sorsix.intern.backend.service.BookService
import com.sorsix.intern.backend.service.ReviewService
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequestMapping("/api/books")
class BooksController(
    val bookService: BookService,
    val reviewService: ReviewService) {
    @GetMapping("")
    fun getBooks() = bookService.findAllBooksForTable();

    @GetMapping("getTopByLetters")
    fun getTopByLetters() = bookService.findBookCardsByLetters();

    @GetMapping("/{id}")
    fun getBookById(@PathVariable id: Long) = bookService.getBookDetailsById(id)

    @PostMapping("/add")
    fun addBook(@RequestBody book: AddBook): Unit {
        bookService.addBook(book);
    }

    @GetMapping("/availability/{id}")
    fun getBookAvailability(@PathVariable id: Long) = bookService.getBookAvailability(id)

    @DeleteMapping("/delete/{id}")
    fun deleteBook(@PathVariable id: Long) = bookService.delete(id)

    @PostMapping("/review/{id}")
    fun leaveReview(@PathVariable id: Long, @RequestBody review: AddReview) = reviewService.createReview(id, review)
}