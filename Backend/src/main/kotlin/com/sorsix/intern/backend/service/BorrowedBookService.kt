package com.sorsix.intern.backend.service

import com.sorsix.intern.backend.domain.BorrowBook

interface BorrowedBookService {
    fun findAllByIdContaining(borrowedBooksId: List<Long>) : MutableList<BorrowBook>
}