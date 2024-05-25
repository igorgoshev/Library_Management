package com.sorsix.intern.backend.api.dtos

import com.sorsix.intern.backend.domain.Library
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

data class LibraryStore(
    val id: Long = 0,
    val address: String = "",
    val name: String = "",
    val imgUrl: String = "",
    var libraryName: String = "",
)
