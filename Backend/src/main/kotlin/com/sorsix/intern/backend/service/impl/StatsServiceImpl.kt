package com.sorsix.intern.backend.service.impl

import com.sorsix.intern.backend.domain.views.LoansInLastDays
import com.sorsix.intern.backend.domain.views.ReservationsInLastDays
import com.sorsix.intern.backend.domain.views.YearlyBorrows
import com.sorsix.intern.backend.domain.views.YearlyReservations
import com.sorsix.intern.backend.repository.*
import com.sorsix.intern.backend.service.StatsService
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.*

@Service
class StatsServiceImpl(
    private val storeInventoryRepository: StoreInventoryRepository,
    private val librarianRepository: LibrarianRepository,
    private val loansInLastDays: LoansInLastDaysRepository,
    private val reservationsInLastDaysRepository: ReservationsInLastDaysRepository,
    private val yearlyReservationsRepository: YearlyReservationsRepository,
    private val yearlyBorrowsRepository: YearlyBorrowsRepository,
    private val bookRepository: BookRepository,
    private val authorRepository: AuthorRepository,
    private val libraryStoreRepository: LibraryStoreRepository,
    private val categoryRepository: CategoryRepository,
    private val bookInLibraryRepository: BookInLibraryRepository,
) : StatsService {
    override fun getInventoryForStore(userId: Long): Double {
        val storeId = librarianRepository.findStoreIdByUserId(userId)
        val stats = storeInventoryRepository.findByIdOrNull(storeId) ?: throw NotFoundException()
        return stats.lentBooks.toDouble() / stats.totalBooks.toDouble() * 100.0
    }

    override fun getLoansInLastDays(userId: Long): LoansInLastDays {
        val storeId = librarianRepository.findStoreIdByUserId(userId)
        return loansInLastDays.findByIdOrNull(storeId) ?: throw NotFoundException()
    }

    override fun getReservationsInLastDays(userId: Long): ReservationsInLastDays {
        val storeId = librarianRepository.findStoreIdByUserId(userId)
        return reservationsInLastDaysRepository.findByIdOrNull(storeId) ?: throw NotFoundException()
    }

    override fun getYearlyReservations(userId: Long): YearlyReservations {
        val storeId = librarianRepository.findStoreIdByUserId(userId)
        return yearlyReservationsRepository.findByIdOrNull(storeId) ?: throw NotFoundException()
    }

    override fun getYearlyBorrows(userId: Long): YearlyBorrows {
        val storeId = librarianRepository.findStoreIdByUserId(userId)
        return yearlyBorrowsRepository.findByIdOrNull(storeId) ?: throw NotFoundException()
    }

    @Scheduled(cron = "* */10 * * * *")
    override fun refreshViews() {
        yearlyBorrowsRepository.refreshView();
        yearlyReservationsRepository.refreshView();
        reservationsInLastDaysRepository.refreshView();
        loansInLastDays.refreshView()
        bookRepository.refreshView()
        authorRepository.refreshView()
        libraryStoreRepository.refreshView()
        categoryRepository.refreshView()
        storeInventoryRepository.refreshView()
    }

    override fun getRatioForStore(userId: Long): Double {
        val storeId = librarianRepository.findStoreIdByUserId(userId) ?: throw NotFoundException()
        return bookInLibraryRepository.getBooksRatio(storeId);
    }
}