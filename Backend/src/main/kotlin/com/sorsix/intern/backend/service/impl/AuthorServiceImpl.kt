package com.sorsix.intern.backend.service.impl

import com.sorsix.intern.backend.api.dtos.BookCard
import com.sorsix.intern.backend.domain.Author
import com.sorsix.intern.backend.repository.AuthorRepository
import com.sorsix.intern.backend.repository.BookRepository
import com.sorsix.intern.backend.service.AuthorService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class AuthorServiceImpl(
    val repository: AuthorRepository,
    val bookRepository: BookRepository
) : AuthorService {

    override fun findAll(): MutableList<Author> = repository.findAll();

    override fun findById(id: Long): Author? = repository.findById(id).get();

    override fun save(id: Long?, name: String, lastName: String, booksId: List<Long>): Author? {
        return if (id != null){
            val author: Author? = findById(id)
            author?.name = name
            author?.lastName = lastName
            author?.books = bookRepository.findAllByIdIn(booksId);
            author?.let { repository.save(it) }
        } else {
            repository.save(Author(name = name, lastName = lastName, books = bookRepository.findAllByIdIn(booksId)));
        }
    }

    override fun delete(id: Long): Unit =
        repository.deleteById(id)

    override fun findAllByIdContaining(authorsId: List<Long>): MutableList<Author> = repository.findAllByIdIn(authorsId)

    override fun getPopularAuthors(): List<Author> {
        return repository.findPopularAuthors().take(5)
    }

    override fun addOrUpdate(author: com.sorsix.intern.backend.api.dtos.Author): Author {
        val newAuthor = repository.findByIdOrNull(author.id) ?: Author()
        newAuthor.name = author.name
        newAuthor.lastName = author.lastName
        return repository.save(newAuthor)
    }
}