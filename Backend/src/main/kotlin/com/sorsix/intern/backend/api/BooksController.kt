package com.sorsix.intern.backend.api

import com.sorsix.intern.backend.api.dtos.*
import com.sorsix.intern.backend.domain.Book
import com.sorsix.intern.backend.domain.BookInLibrary
import com.sorsix.intern.backend.service.BookService
import com.sorsix.intern.backend.service.ReviewService
import com.sorsix.intern.backend.service.WishListService
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequestMapping("/api/books")
class BooksController(
    val bookService: BookService,
    val reviewService: ReviewService,
    val wishListService: WishListService) {
    @GetMapping("")
    fun getBooks(): List<BookInTable> {
        return bookService.findAllBooksForTable()
    }

    @GetMapping("getTopByLetters")
    fun getTopByLetters() = bookService.findBookCardsByLetters();

    @GetMapping("getAllByLetters")
    fun getAllByLetters(@RequestParam(required = false) letter: Char?): Map<Char, List<AvailableBooks>> {
        return if(letter == null) bookService.findAvailableBooksByLetter(null)
        else bookService.findAvailableBooksByLetter(letter);
    }

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
    
    @GetMapping("/reviews/{id}")
    fun getReviewsByBook(@PathVariable id: Long) = reviewService.getReviewsByBook(id);

    @PostMapping("/lend")
    fun lendBook(@RequestBody lendBook: LendBook) {
        bookService.lendBook(lendBook.userId, lendBook.copyId)
    }

    @GetMapping("/wishlist/add/{id}")
    fun addBookToWishList(@PathVariable id: Long) {
        wishListService.addBookToWishList(id, 1)
    }

    @GetMapping("/wishlist/exist/{id}")
    fun bookExistInWishList(@PathVariable id: Long) : Boolean =
        wishListService.bookExistInWishList(id, 1)

}