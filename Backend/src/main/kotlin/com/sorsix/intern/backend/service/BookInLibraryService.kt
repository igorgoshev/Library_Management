package com.sorsix.intern.backend.service

import com.sorsix.intern.backend.domain.BookInLibrary
import com.sorsix.intern.backend.domain.dto.BookInLibraryDto

interface BookInLibraryService {
    fun findAllByIdContaining(bookInLibrariesId: List<Long>): MutableList<BookInLibrary>
    fun findById(id: Long): BookInLibrary?
    fun findAll(): List<BookInLibrary>
    fun delete(id: Long): BookInLibrary?
    fun save(id: Long?, bookInLibraryDto: BookInLibraryDto): BookInLibrary?
}