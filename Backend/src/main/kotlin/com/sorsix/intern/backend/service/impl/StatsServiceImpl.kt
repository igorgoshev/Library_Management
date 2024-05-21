package com.sorsix.intern.backend.service.impl

import com.sorsix.intern.backend.domain.views.LoansInLastDays
import com.sorsix.intern.backend.domain.views.ReservationsInLastDays
import com.sorsix.intern.backend.domain.views.YearlyBorrows
import com.sorsix.intern.backend.domain.views.YearlyReservations
import com.sorsix.intern.backend.repository.*
import com.sorsix.intern.backend.service.StatsService
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.data.repository.findByIdOrNull
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
) : StatsService {
    override fun getInventoryForStore(userId: Long): Double {
        val storeId = librarianRepository.findLibraryIdByUserId(userId)
        val stats = storeInventoryRepository.findByIdOrNull(storeId) ?: throw NotFoundException()
        return stats.lentBooks.toDouble() / stats.totalBooks.toDouble() * 100.0
    }

    override fun getLoansInLastDays(userId: Long): LoansInLastDays {
        val storeId = librarianRepository.findLibraryIdByUserId(userId)
        return loansInLastDays.findByIdOrNull(storeId) ?: throw NotFoundException()
    }

    override fun getReservationsInLastDays(userId: Long): ReservationsInLastDays {
        val storeId = librarianRepository.findLibraryIdByUserId(userId)
        return reservationsInLastDaysRepository.findByIdOrNull(storeId) ?: throw NotFoundException()
    }

    override fun getYearlyReservations(userId: Long): YearlyReservations {
        val storeId = librarianRepository.findLibraryIdByUserId(userId)
        return yearlyReservationsRepository.findByIdOrNull(storeId) ?: throw NotFoundException()
    }

    override fun getYearlyBorrows(userId: Long): YearlyBorrows {
        val storeId = librarianRepository.findLibraryIdByUserId(userId)
        return yearlyBorrowsRepository.findByIdOrNull(storeId) ?: throw NotFoundException()
    }
}