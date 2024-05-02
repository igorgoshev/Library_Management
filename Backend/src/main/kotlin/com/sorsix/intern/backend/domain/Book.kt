package com.sorsix.intern.backend.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*
import java.time.LocalDate

@Entity
class Book (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    var name: String,
    var publishedYear: LocalDate,
    var imgUrl: String,
    var description: String,

    @ManyToOne
    var publishingHouse: PublishingHouse,

    @ManyToMany
    @JoinTable(
        name = "book_has",
        joinColumns = [JoinColumn(name = "book_id")],
        inverseJoinColumns = [JoinColumn(name = "author_id")]
    )
    @JsonIgnoreProperties("books")
    var authors: MutableList<Author>,

    @OneToMany(mappedBy = "book")
    var bookInLibrary: MutableList<BookInLibrary>,

    @ManyToMany
    @JoinTable(
        name = "belongs",
        joinColumns = [JoinColumn(name = "book_id")],
        inverseJoinColumns = [JoinColumn(name = "category_id")]
    )
    var categories: MutableList<Category>,

    @ManyToMany(mappedBy = "books")
    var wishLists: MutableList<WishList>,

    @OneToMany(mappedBy = "book")
    var reviews: MutableList<Review>,

    @OneToMany(mappedBy = "book")
    var customerBooks: MutableList<CustomerBook>
){
}