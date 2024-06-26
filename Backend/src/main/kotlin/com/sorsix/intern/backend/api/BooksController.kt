package com.sorsix.intern.backend.api

import com.sorsix.intern.backend.api.dtos.*
import com.sorsix.intern.backend.config.CurrentUser
import com.sorsix.intern.backend.domain.BookInLibrary
import com.sorsix.intern.backend.domain.CustomerBook
import com.sorsix.intern.backend.security.UserPrincipal
import com.sorsix.intern.backend.service.*
import com.sorsix.intern.backend.service.impl.BookInLibraryServiceImpl
import com.sorsix.intern.backend.service.impl.BorrowedBookServiceImpl
import com.sorsix.intern.backend.service.impl.CategoryServiceImpl
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@CrossOrigin
@RequestMapping("/api/books")
class BooksController(
    private val bookService: BookService,
    private val reviewService: ReviewService,
    private val wishListService: WishListService,
    private val reservedBookService: ReservedBookService,
    private val borrowedBookService: BorrowedBookService,
    private val categoryService: CategoriesService,
    private val bookInLibraryService: BookInLibraryService,
    private val customerBookService: CustomerBookService
) {
    @GetMapping("")
    fun getBooks(): List<BookInTable> {
        return bookService.findAllBooksForTable()
    }

    @GetMapping("getTopByLetters")
    fun getTopByLetters() = bookService.findBookCardsByLetters();

    @GetMapping("getAllBooksByLetters")
    fun getAllBooksByLetters(@RequestParam(required = false) letter: Char?): Map<Char, List<BookCard>> {
        return if(letter == null) bookService.findAllAvailableBooksByLetter(null)
        else bookService.findAllAvailableBooksByLetter(letter)
    }

    @GetMapping("getBooksContaining")
    fun getBooksContaining(
        @RequestParam(required = false) query: String,
        @RequestParam(required = false) category: String
    ): Map<Char?, List<BookCard>> {
        return bookService.getBooksContaining(query, category)
    }

    @GetMapping("getBooksContainingAdmin")
    fun getBooksContainingAdmin(@RequestParam query: String): Map<Char?, List<AvailableBooks>> {
        return bookService.getBooksContainingAdmin(query)
    }

    @GetMapping("getAllByLetters")
    fun getAllByLetters(@RequestParam(required = false) letter: Char?): Map<Char, List<AvailableBooks>> {
        return if(letter == null) bookService.findAvailableBooksByLetter(null)
        else bookService.findAvailableBooksByLetter(letter);
    }

    @GetMapping("/{id}")
    fun getBookById(@PathVariable id: Long) = bookService.getBookDetailsById(id)

    @PostMapping("/add")
    fun addBook(@RequestPart("book") book: AddBook, @RequestPart("imgFile") file: MultipartFile): Unit {
        bookService.addBook(book, file);
    }

    @GetMapping("/availability/{id}")
    fun getBookAvailability(@PathVariable id: Long) = bookService.getBookAvailability(id)

    @DeleteMapping("/delete/{id}")
    fun deleteBook(@PathVariable id: Long) = bookService.delete(id)

    @PostMapping("/review/{id}")
    fun leaveReview(@PathVariable id: Long, @RequestBody review: AddReview, @CurrentUser userPrincipal: UserPrincipal) = reviewService.createReview(id, review, userPrincipal.id)
    
    @GetMapping("/reviews/{id}")
    fun getReviewsByBook(@PathVariable id: Long) = reviewService.getReviewsByBook(id);

    @PostMapping("/lend")
    fun lendBook(@RequestBody lendBook: LendBook) {
        bookService.lendBook(lendBook.userId, lendBook.copyId)
    }

    @GetMapping("/wishlist/exist/{id}")
    fun bookExistInWishList(@PathVariable id: Long, @CurrentUser userPrincipal: UserPrincipal) : Boolean =
        wishListService.bookExistInWishList(id, userPrincipal.id)

    @GetMapping("/wishlist/add/{id}")
    fun addBookToWishList(@PathVariable id: Long, @CurrentUser userPrincipal: UserPrincipal) {
        wishListService.addBookToWishList(id, userPrincipal.id)
    }

    @GetMapping("/wishlist/delete/{id}")
    fun deleteFromWishlist(@PathVariable id: Long, @CurrentUser userPrincipal: UserPrincipal) : Boolean =
        wishListService.deleteBook(id, userPrincipal.id)

    @GetMapping("/reserve/{bookId}/{storeId}")
    fun reserveBook(@PathVariable bookId: Long, @PathVariable storeId: Long){
        return reservedBookService.reserveBook(bookId = bookId, storeId = storeId, userId = 1)
    }

    @GetMapping("/reserve/exist/{bookId}")
    fun reservationExist(@PathVariable bookId: Long) : Boolean{
        return reservedBookService.reservationExist(bookId = bookId, userId = 1)
    }

    @GetMapping("/lent/active")
    fun getActiveLendingsByStoreId(@CurrentUser userPrincipal: UserPrincipal): List<LentBookDetails> {
        return borrowedBookService.findAllActiveByStoreId(userPrincipal.id);
    }

    @PostMapping("/lent/{lendingId}/finish")
    fun finishLending(@PathVariable lendingId: Long) {
        borrowedBookService.finishLending(lendingId)
    }

    @GetMapping("/reservationsForUser")
    fun getReservationsForUser(@CurrentUser userPrincipal: UserPrincipal): List<ReservedBookDetails> {
        return reservedBookService.findAllByCustomerId(userPrincipal.id)
        }

    @PostMapping("/reservations/{reservationId}/cancel")
    fun cancelReservation(@PathVariable reservationId: Long) {
        reservedBookService.cancelReservation(reservationId)
    }

    @GetMapping("/wishlist")
    fun getWishListForCustomer(@CurrentUser userPrincipal: UserPrincipal): List<BookInTable> {
        return wishListService.findAllByCustomerId(userPrincipal.id);
    }

    @GetMapping("/loans")
    fun getLoansForUser(@CurrentUser userPrincipal: UserPrincipal): List<BookCard> {
        return borrowedBookService.findAllActiveByCustomerId(userPrincipal.id);
    }

    @GetMapping("/popular")
    fun getPopularBooks(): List<BookCard> {
        return bookService.getPopularBooks();
    }

    @GetMapping("/{bookId}/copies")
    fun getCopies(@PathVariable bookId: Long, @CurrentUser userPrincipal: UserPrincipal): List<AvailableBook> {
        return bookService.getBookCopies(bookId, userPrincipal.id)
    }

    @PostMapping("/copies/add")
    fun addCopies(@RequestBody addCopy: AddCopy, @CurrentUser userPrincipal: UserPrincipal) {
        return bookInLibraryService.addCopies(addCopy, userPrincipal.id)
    }

    @GetMapping("/copies/{id}/delete")
    fun deleteCopy(@PathVariable id: Long) {
        return bookInLibraryService.deleteCopy(id)
    }

    @GetMapping("/reservations")
    fun getReservationsForStore(@CurrentUser userPrincipal: UserPrincipal): List<LentBookDetails> {
        return reservedBookService.getReservationsForStore(userPrincipal.id)
    }

    @GetMapping("/reservations/{id}/finish")
    fun finishReservation(@PathVariable id: Long, @CurrentUser userPrincipal: UserPrincipal) {
        reservedBookService.finishReservation(id, userPrincipal.id)
    }

    @GetMapping("/customer/all")
    fun getAllCustomerBooks(@CurrentUser userPrincipal: UserPrincipal): List<CustomerBookCard> {
        return bookService.getCustomerBooks(userPrincipal.id)
    }

    @GetMapping("/customer/{id}")
    fun getAllCustomerBookTrades(@CurrentUser userPrincipal: UserPrincipal, @PathVariable id: Long): List<LentBookDetails> {
        return bookService.getAllTradesByCustomerBook(id);
    }

    @GetMapping("/customer/add/{id}")
    fun addBookToCustomerCollection(@CurrentUser userPrincipal: UserPrincipal, @PathVariable id: Long) {
        bookService.addBookToCustomer(userPrincipal.id, id)
    }

    @GetMapping("/customersContaining/{bookId}")
    fun getCustomerBooks(@PathVariable bookId: Long) : List<com.sorsix.intern.backend.api.dtos.CustomerBook> {
        return customerBookService.getCollectionOfBookUser(bookId);
    }

    @GetMapping("/lend/{id}")
    fun lendBookToCustomer(@PathVariable id: Long, @CurrentUser userPrincipal: UserPrincipal){
        return customerBookService.lendBookToCustomer(id, userPrincipal.id)
    }

    @GetMapping("/customer/{id}/finish")
    fun returnCustomerBook(@PathVariable id: Long, @CurrentUser userPrincipal: UserPrincipal) {
        customerBookService.returnBook(id)
    }
}