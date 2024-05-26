package com.sorsix.intern.backend.service.impl

import com.sorsix.intern.backend.api.dtos.BookCard
import com.sorsix.intern.backend.api.dtos.BookInTable
import com.sorsix.intern.backend.api.dtos.LentBookDetails
import com.sorsix.intern.backend.api.dtos.UserAvatar
import com.sorsix.intern.backend.domain.Book
import com.sorsix.intern.backend.domain.BorrowBook
import com.sorsix.intern.backend.repository.BorrowBookRepository
import com.sorsix.intern.backend.repository.LibrarianRepository
import com.sorsix.intern.backend.repository.WishListRepository
import com.sorsix.intern.backend.service.BorrowedBookService
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Service
class BorrowedBookServiceImpl(
    val repository: BorrowBookRepository,
    private val librarianRepository: LibrarianRepository,
    private val mailingServiceImpl: MailingServiceImpl,
    private val wishListRepository: WishListRepository
) : BorrowedBookService {
    override fun findAllByIdContaining(borrowedBooksId: List<Long>): MutableList<BorrowBook> =
        repository.findAllByIdIn(borrowedBooksId);

    override fun findAllActiveByStoreId(userId: Long): List<LentBookDetails> {
        val storeId = librarianRepository.findStoreIdByUserId(userId) ?: throw NotFoundException()
        val books = repository.findAllByBookInLibrary_LibraryStore_IdAndDateToIsNull(storeId)
        return books.map {
            LentBookDetails(
                dateFrom = it.dateFrom,
                dateTo = it.dateTo,
                id = it.id,
                customer = UserAvatar(
                    id = it.customer.id,
                    name = it.customer.name,
                    email = it.customer.email,
                    imgUrl = it.customer.imgUser ?: "",
                    lastName = ""
                ),
                book = BookInTable(
                    id = it.bookInLibrary.book?.id,
                    name = it.bookInLibrary.book?.name,
                    description = it.bookInLibrary.book?.description ?: "",
                    imgUrl = it.bookInLibrary.book?.imgUrl,
                    isbn = it.bookInLibrary.book?.isbn ?: "",
                    publisher = it.bookInLibrary.book?.publishingHouse?.name ?: ""
                )
            )
        }
    }

    override fun finishLending(lendingId: Long) {
        val book = repository.findByIdOrNull(lendingId) ?: throw RuntimeException()
        book.dateTo = LocalDate.now()
        book.bookInLibrary.isLent = false;
        book.bookInLibrary.isReserved = false;
        repository.save(book)
        notifyForBookBackAvailable(book.bookInLibrary.book ?: throw RuntimeException())
    }

    private fun notifyForBookBackAvailable(book: Book) {
        val wishlistEntries = wishListRepository.findAllByBooksContaining(book)
        val emailSender = Executors.newSingleThreadExecutor()
        emailSender.execute {
            wishlistEntries.forEach {
                mailingServiceImpl.sendMail(
                    to = it.customer.email,
                    subject = "${book.name} Available Now!",
                    message = "Hi ${it.customer.name},\n" +
                            "\n" +
                            "Great news! The book you've been waiting for, \"${book.name},\" is back in stock and ready for you to grab. \uD83D\uDCDA✨\n" +
                            "\n Happy reading!\n" +
                            "\n" +
                            "Best,"
                )
            }
        }
        emailSender.shutdown()
    }

    override fun findAllActiveByCustomerId(userId: Long): List<BookCard> {
        val books = repository.findAllByCustomer_Id(userId)
        return books.map { it.bookInLibrary.book }
            .map {
                BookCard(
                    id = it?.id ?: 0,
                    name = it?.name ?: "",
                    authors = it?.authors?.map { it.name + " " + it.lastName }?.toList(),
                    imgUrl = it?.imgUrl ?: "",
                )
            }
    }

    @Scheduled(cron = "0 0 8 * * *")
    private fun notifyOverdue() {
        val overdues = repository.findAllOverdue();
        overdues.forEach {
            mailingServiceImpl.sendMail(
                to = it.customer.email,
                subject = "Overdue Book Loan Notification",
                message = "Dear ${it.customer.name},\n" +
                        "\n" +
                        "We hope this message finds you well. This is a friendly reminder that your book loan for \"${it.bookInLibrary.book?.name}\" is now overdue. Please return the book at your earliest convenience to avoid any late fees.\n" +
                        "\n" +
                        "If you have any questions or need assistance, feel free to contact us.\n" +
                        "\n" +
                        "Thank you for your prompt attention to this matter.\n" +
                        "\n" +
                        "Best regards,"
            )
        }
    }
}