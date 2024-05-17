package com.sorsix.intern.backend.service

import com.sorsix.intern.backend.domain.ReserveBook

interface ReservedBookService {
    fun findAllByIdContaining(reservedBooks: List<Long>) : MutableList<ReserveBook>
    fun reserveBook(bookId: Long, storeId: Long, userId: Long)

    fun reservationExist(bookId: Long, userId: Long): Boolean
}