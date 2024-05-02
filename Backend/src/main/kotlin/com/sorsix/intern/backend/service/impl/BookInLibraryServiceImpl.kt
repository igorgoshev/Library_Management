package com.sorsix.intern.backend.service.impl

import com.sorsix.intern.backend.domain.BookInLibrary
import com.sorsix.intern.backend.domain.dto.BookInLibraryDto
import com.sorsix.intern.backend.repository.BookInLibraryRepository
import com.sorsix.intern.backend.repository.BookRepository
import com.sorsix.intern.backend.repository.LibraryStoreRepository
import com.sorsix.intern.backend.service.*
import org.springframework.stereotype.Service

@Service
class BookInLibraryServiceImpl(
    val repository: BookInLibraryRepository,
    val bookRepository: BookRepository,
    val borrowedBookService: BorrowedBookService,
    val reservedBookService: ReservedBookService,
    val libraryStoreRepository: LibraryStoreRepository

    ) : BookInLibraryService {
    override fun findAllByIdContaining(bookInLibrariesId: List<Long>): MutableList<BookInLibrary> =
        repository.findAllByIdIn(bookInLibrariesId)

    override fun findById(id: Long): BookInLibrary? = repository.findById(id).get()

    override fun findAll(): List<BookInLibrary> = repository.findAll();

    override fun delete(id: Long): BookInLibrary? {
        return findById(id)?.let {
            repository.delete(it)
            it
        }
    }

    override fun save(id: Long?, bookInLibraryDto: BookInLibraryDto): BookInLibrary? {
        return if (id != null) {
            val bookInLibrary: BookInLibrary? = findById(id);
            bookInLibrary?.book = bookRepository.findById(bookInLibraryDto.bookId).get()
            bookInLibrary?.borrowedBooks =  borrowedBookService.findAllByIdContaining(bookInLibraryDto.borrowedBooksId)
            bookInLibrary?.reservedBooks =  reservedBookService.findAllByIdContaining(bookInLibraryDto.borrowedBooksId)
            bookInLibrary?.condition = bookInLibraryDto.condition
            bookInLibrary?.libraryStore = libraryStoreRepository.findById(bookInLibraryDto.libraryStoreId).get()
            bookInLibrary?.let { repository.save(it) }
        }
        else {
            repository.save(
                BookInLibrary(
                    id = null,
                    book = bookRepository.findById(bookInLibraryDto.bookId).get(),
                    borrowedBooks = borrowedBookService.findAllByIdContaining(bookInLibraryDto.borrowedBooksId),
                    reservedBooks = reservedBookService.findAllByIdContaining(bookInLibraryDto.borrowedBooksId),
                    condition = bookInLibraryDto.condition,
                    libraryStore = libraryStoreRepository.findById(bookInLibraryDto.libraryStoreId).get()
                )
            )
        }
    }
}