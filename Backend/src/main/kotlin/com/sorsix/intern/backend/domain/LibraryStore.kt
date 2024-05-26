package com.sorsix.intern.backend.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany

@Entity
class LibraryStore(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    var address: String = "",
    var name: String = "",
    var imgUrl: String = "",
    @ManyToOne
    var library: Library? = null,
    @OneToMany(mappedBy = "libraryStore")
    var bookInLibrary: MutableList<BookInLibrary> = mutableListOf()
) {

}