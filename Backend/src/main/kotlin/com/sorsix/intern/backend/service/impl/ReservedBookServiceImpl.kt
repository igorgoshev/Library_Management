package com.sorsix.intern.backend.service.impl

import com.sorsix.intern.backend.domain.*
import com.sorsix.intern.backend.repository.*
import com.sorsix.intern.backend.service.ReservedBookService
import com.sorsix.intern.backend.service.UserService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ReservedBookServiceImpl(
    val repository: ReserveBookRepository,
    val bookRepository: BookRepository,
    val libraryStoreRepository: LibraryStoreRepository,
    val userService: UserService,
    val bookInLibraryRepository: BookInLibraryRepository,
) : ReservedBookService {
    override fun findAllByIdContaining(reservedBooks: List<Long>): MutableList<ReserveBook> = repository.findAllByIdIn(reservedBooks);
    override fun reserveBook(bookId: Long, storeId: Long, userId: Long) {

        val customer: Customer = userService.findById(userId) ?: run {
            throw Exception()
        }

        val bookInLibrary: BookInLibrary = bookInLibraryRepository.findFirstByLibraryStore_IdAndBook_Id(storeId, bookId)

        repository.save(ReserveBook(id = null, dateFrom = LocalDate.now(), dateTo = null, customer = customer, bookInLibrary = bookInLibrary ))
    }

    override fun reservationExist(bookId: Long, userId: Long): Boolean {

        return repository.countByCustomer_IdAndBookInLibrary_Book_IdAndDateToNull(customerId = userId, bookId = bookId) > 0


    }
}