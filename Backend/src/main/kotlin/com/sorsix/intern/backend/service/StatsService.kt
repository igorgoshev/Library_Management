package com.sorsix.intern.backend.service

import com.sorsix.intern.backend.domain.views.*

interface StatsService {
    fun getInventoryForStore(userId: Long): Double
    fun getLoansInLastDays(userId: Long): LoansInLastDays
    fun getReservationsInLastDays(userId: Long): ReservationsInLastDays
    fun getYearlyBorrows(userId: Long): YearlyBorrows
    fun getYearlyReservations(userId: Long): YearlyReservations
    fun refreshViews()
}