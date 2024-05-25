package com.sorsix.intern.backend.api

import com.sorsix.intern.backend.api.dtos.Author
import com.sorsix.intern.backend.service.AuthorService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/authors")
@CrossOrigin
class AuthorsController(
    val authorService: AuthorService
) {
    @GetMapping("")
    fun getAuthors() = authorService.findAll();

    @GetMapping("/popular")
    fun getPopularAuthors() = authorService.findAll()

    @PostMapping("")
    fun addOrUpdateAuthors(@RequestBody author: Author): com.sorsix.intern.backend.domain.Author {
        return authorService.addOrUpdate(author)
    }

    @DeleteMapping("/{id}")
    fun deleteAuthor(@PathVariable id: Long) {
        authorService.delete(id)
    }
}