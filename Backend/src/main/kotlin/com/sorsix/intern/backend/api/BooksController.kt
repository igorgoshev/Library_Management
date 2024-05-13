package com.sorsix.intern.backend.api

import com.sorsix.intern.backend.api.dtos.AddBook
import com.sorsix.intern.backend.service.BookService
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequestMapping("/api/books")
class BooksController(val bookService: BookService) {
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

    @DeleteMapping("/delete/{id}")
    fun deleteBook(@PathVariable id: Long) = bookService.delete(id)
}