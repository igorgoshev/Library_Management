package com.sorsix.intern.backend.service.impl

import com.sorsix.intern.backend.domain.BorrowBook
import com.sorsix.intern.backend.repository.BorrowBookRepository
import com.sorsix.intern.backend.service.BorrowedBookService
import org.springframework.stereotype.Service

@Service
class BorrowedBookServiceImpl(val repository: BorrowBookRepository) : BorrowedBookService{
    override fun findAllByIdContaining(borrowedBooksId: List<Long>): MutableList<BorrowBook> = repository.findAllByIdIn(borrowedBooksId);
}