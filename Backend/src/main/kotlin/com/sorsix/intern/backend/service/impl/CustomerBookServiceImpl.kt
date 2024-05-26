package com.sorsix.intern.backend.service.impl

import com.sorsix.intern.backend.api.dtos.UserAvatar
import com.sorsix.intern.backend.domain.Customer
import com.sorsix.intern.backend.domain.CustomerBook
import com.sorsix.intern.backend.domain.Trade
import com.sorsix.intern.backend.repository.CustomerBookRepository
import com.sorsix.intern.backend.repository.CustomerRepository
import com.sorsix.intern.backend.repository.TradeRepository
import com.sorsix.intern.backend.service.CustomerBookService
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class CustomerBookServiceImpl(
    val repository: CustomerBookRepository,
    val tradeRepository: TradeRepository,
    val customerRepository: CustomerRepository) : CustomerBookService {
    override fun findAllByIdContaining(customerBooksId: List<Long>): MutableList<CustomerBook> = repository.findAllByIdIn(customerBooksId)
    override fun getCollectionOfBookUser(bookId: Long) : List<com.sorsix.intern.backend.api.dtos.CustomerBook> {
        return repository.findAllByBook_IdAndAvailableIsTrue(bookId).map {
            com.sorsix.intern.backend.api.dtos.CustomerBook(
                customer = UserAvatar(
                    name = it.customer.name,
                    lastName = it.customer.name,
                    imgUrl = "",
                    id = it.customer.id,
                    email = it.customer.email),
                available = it.available,
                id = it.id
            )
        };
    }
    override fun lendBookToCustomer(customerBookId: Long, customerId: Long) {

        val customerBook: CustomerBook = repository.findByIdOrNull(customerBookId) ?: throw NotFoundException()
        customerBook.available = false
        repository.save(customerBook)
        tradeRepository.save(Trade(id = null, customer = customerRepository.findByIdOrNull(customerId) ?: throw NotFoundException(), dateFrom = LocalDate.now(), customerBook = customerBook, dateTo = null ))


    }
}