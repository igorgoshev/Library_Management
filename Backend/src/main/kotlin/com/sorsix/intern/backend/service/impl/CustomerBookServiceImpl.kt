package com.sorsix.intern.backend.service.impl
import com.sorsix.intern.backend.service.MailingService
import com.sorsix.intern.backend.service.impl.MailingServiceImpl


import com.sorsix.intern.backend.api.dtos.UserAvatar
import com.sorsix.intern.backend.domain.CustomerBook
import com.sorsix.intern.backend.domain.Trade
import com.sorsix.intern.backend.repository.CustomerBookRepository
import com.sorsix.intern.backend.repository.CustomerRepository
import com.sorsix.intern.backend.repository.TradeRepository
import com.sorsix.intern.backend.service.CustomerBookService
import jakarta.transaction.Transactional
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class CustomerBookServiceImpl(
    val repository: CustomerBookRepository,
    val tradeRepository: TradeRepository,
    val customerRepository: CustomerRepository,
    private val mailingServiceImpl: MailingService
) : CustomerBookService {
    override fun findAllByIdContaining(customerBooksId: List<Long>): MutableList<CustomerBook> =
        repository.findAllByIdIn(customerBooksId)

    override fun getCollectionOfBookUser(bookId: Long): List<com.sorsix.intern.backend.api.dtos.CustomerBook> {
        return repository.findAllByBook_IdAndAvailableIsTrue(bookId).map {
            com.sorsix.intern.backend.api.dtos.CustomerBook(
                customer = UserAvatar(
                    name = it.customer.name,
                    lastName = it.customer.name,
                    imgUrl = "",
                    id = it.customer.id,
                    email = it.customer.email
                ),
                available = it.available,
                id = it.id
            )
        };
    }

    @Transactional
    override fun lendBookToCustomer(customerBookId: Long, customerId: Long) {
        val customerBook: CustomerBook = repository.findByIdOrNull(customerBookId) ?: throw NotFoundException()
        val customer = customerRepository.findByIdOrNull(customerId) ?: throw NotFoundException()
        customerBook.available = false
        repository.save(customerBook)
        tradeRepository.save(
            Trade(
                id = null,
                customer = customer,
                dateFrom = LocalDate.now(),
                customerBook = customerBook,
                dateTo = null
            )
        )
        mailingServiceImpl.sendMail(
            to = customerBook.customer.email,
            subject = "Book Reservation Confirmed",
            message = "Dear ${customerBook.customer.name},\n" +
                    "\n" +
                    "We are pleased to inform you that your reservation for \"${customerBook.book.name}\" has been confirmed.\n" +
                    "\n" +
                    "Please pick up your book in contact with the owner, at this address ${customerBook.customer.address}. If you need assistance, feel free to contact the owner on ${customerBook.customer.email}.\n" +
                    "\n" +
                    "Thank you for choosing us.\n" +
                    "\n" +
                    "Best regards,"
        )

        mailingServiceImpl.sendMail(
            to = customer.email,
            subject = "Book Reservation Notification",
            message = "Dear ${customer.name},\n" +
                    "\n" +
                    "Your book, \"${customerBook.book.name},\" has been reserved by [Customer's Name].\n" +
                    "\n" +
                    "Please have it ready for pickup.\n" +
                    "\nYou can contact the person who reserved on ${customerBook.customer.email}" +
                    "Thank you.\n" +
                    "\n" +
                    "Best regards,"
        )
    }

    override fun returnBook(id: Long) {
        val trade = tradeRepository.findByIdOrNull(id) ?: throw NotFoundException()
        trade.dateTo = LocalDate.now()
        trade.customerBook.available = true
        tradeRepository.save(trade)
    }
}