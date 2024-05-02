package com.sorsix.intern.backend.service.impl

import com.sorsix.intern.backend.domain.ReserveBook
import com.sorsix.intern.backend.repository.ReserveBookRepository
import com.sorsix.intern.backend.service.ReservedBookService
import org.springframework.stereotype.Service

@Service
class ReservedBookServiceImpl(val repository: ReserveBookRepository) : ReservedBookService {
    override fun findAllByIdContaining(reservedBooks: List<Long>): MutableList<ReserveBook> = repository.findAllByIdIn(reservedBooks);
}