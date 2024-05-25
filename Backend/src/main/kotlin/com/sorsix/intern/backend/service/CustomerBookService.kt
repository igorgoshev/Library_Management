package com.sorsix.intern.backend.service

import com.sorsix.intern.backend.domain.Customer
import com.sorsix.intern.backend.domain.CustomerBook

interface CustomerBookService {
    fun findAllByIdContaining(customerBooksId: List<Long>) : MutableList<CustomerBook>

    fun getCollectionOfBookUser(bookId: Long): List<com.sorsix.intern.backend.api.dtos.CustomerBook>;
    fun lendBookToCustomer(customerBookId: Long, customerId: Long)

}