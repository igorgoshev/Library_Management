package com.sorsix.intern.backend.api

import com.sorsix.intern.backend.service.BookService
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequestMapping("/api/books")
class BooksController(val bookService: BookService) {
    @GetMapping("")
    fun getBooks() = bookService.findAllBooksForTable();
}