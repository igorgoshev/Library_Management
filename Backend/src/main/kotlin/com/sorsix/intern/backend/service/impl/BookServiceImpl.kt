package com.sorsix.intern.backend.service.impl

import com.sorsix.intern.backend.api.dtos.BookCard
import com.sorsix.intern.backend.api.dtos.BookInTable
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
        return if (id != null) {
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
        } else {
            repository.save(
                Book(
                    null, name = name, publishedYear = publishedYear, imgUrl = imgUrl,
                    authors = authorService.findAllByIdContaining(authorsId),
                    bookInLibrary = bookInLibraryService.findAllByIdContaining(bookInLibraryId),
                    categories = categoriesService.findAllByIdContaining(categoriesId),
                    wishLists = wishListService.findAllByIdContaining(wishListsId),
                    reviews = reviewService.findAllByIdContaining(reviewsId),
                    customerBooks = customerBookService.findAllByIdContaining(customerBooksId),
                    publishingHouse = publishingHouseService.findById(publishingHouseId),
                    description = description,
                    isbn = "",
                    numPages = 0
                )
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
    override fun findAllBooksForTable(): List<BookInTable> {
        val books = repository.findAll();
        return books.map { it ->
            BookInTable(
                id = it.id,
                name = it.name,
                publisher = it.publishingHouse.name,
                authors = it.authors.map { author: Author ->
                    "${author.name} ${author.lastName}"
                }.toList(),
                categories = it.categories.map { category: Category ->
                    category.name
                }.toList(),
                isbn = "",
                imgUrl = it.imgUrl,
                averageRating = it.reviews.takeIf { it.isNotEmpty() }?.map { it.rate }?.average() ?: 0.0
            )
        }.toList();
    }

    override fun findBookCardsByLetters(): Map<Char, List<BookCard>> {
        val books = repository.findAll();
        return books.groupBy { it.name.first() }.mapValues { (k, v) ->
            v.shuffled().take(12).map {
                BookCard(
                    id = it.id ?: 0,
                    name = it.name,
                    authors = it.authors.map { it.name + " " + it.lastName}.toList(),
                    imgUrl = it.imgUrl,
                )
            };
        }
    }
}