package com.sorsix.intern.backend.service

import com.sorsix.intern.backend.domain.Author
import java.util.*

interface AuthorService {
    fun findAll() : List<Author>
    fun findById(id: Long) : Author?
    fun save(id: Long?, name: String, lastName: String, booksId: List<Long>) : Author?
    fun delete(id: Long)
    fun findAllByIdContaining(authorsId: List<Long>) : MutableList<Author>
    fun getPopularAuthors(): List<Author>
    fun addOrUpdate(author: com.sorsix.intern.backend.api.dtos.Author): Author
}