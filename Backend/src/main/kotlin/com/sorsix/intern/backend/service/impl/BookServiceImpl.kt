package com.sorsix.intern.backend.service.impl

import com.sorsix.intern.backend.domain.*
import com.sorsix.intern.backend.repository.BookRepository
import com.sorsix.intern.backend.service.*
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class BookServiceImpl(
    val repository: BookRepository,
    val publishingHouseService: PublishingHouseService,
    val authorService: AuthorService,
    val bookInLibraryService: BookInLibraryService,
    val categoriesService: CategoriesService,
    val wishListService: WishListService,
    val reviewService: ReviewService,
    val customerBookService: CustomerBookService,


) : BookService {

    override fun findAll(): List<Book> = repository.findAll()

    override fun findById(id: Long): Book? = repository.findById(id).get()

    override fun save(
        id: Long?,
        name: String,
        publishedYear: LocalDate,
        imgUrl: String,
        publishingHouseId: Long,
        authorsId: List<Long>,
        bookInLibraryId: List<Long>,
        categoriesId: List<Long>,
        wishListsId: List<Long>,
        reviewsId: List<Long>,
        customerBooksId: List<Long>,
        description: String
    ): Book? {
        return if (id != null){
            val book: Book? = findById(id);
            book?.name = name
            book?.description = description
            book?.publishedYear = publishedYear
            book?.imgUrl = imgUrl
            book?.publishingHouse = publishingHouseService.findById(publishingHouseId)
            book?.authors = authorService.findAllByIdContaining(authorsId);
            book?.bookInLibrary = bookInLibraryService.findAllByIdContaining(bookInLibraryId)
            book?.categories = categoriesService.findAllByIdContaining(categoriesId)
            book?.wishLists = wishListService.findAllByIdContaining(wishListsId)
            book?.reviews = reviewService.findAllByIdContaining(reviewsId)
            book?.customerBooks = customerBookService.findAllByIdContaining(customerBooksId)
            book?.let { repository.save(it) }
        }
        else {
            repository.save(
                Book(null, name = name, publishedYear = publishedYear, imgUrl = imgUrl,
                    authors = authorService.findAllByIdContaining(authorsId),
                    bookInLibrary = bookInLibraryService.findAllByIdContaining(bookInLibraryId),
                    categories = categoriesService.findAllByIdContaining(categoriesId),
                    wishLists = wishListService.findAllByIdContaining(wishListsId),
                    reviews = reviewService.findAllByIdContaining(reviewsId),
                    customerBooks = customerBookService.findAllByIdContaining(customerBooksId),
                    publishingHouse = publishingHouseService.findById(publishingHouseId),
                    description = description)
            )
        }
    }

    override fun delete(id: Long): Book? {
        return findById(id)?.let {
            repository.delete(it)
            it
        }
    }

    override fun findAllByIdContaining(booksId: List<Long>): MutableList<Book> = repository.findAllByIdIn(booksId)

}