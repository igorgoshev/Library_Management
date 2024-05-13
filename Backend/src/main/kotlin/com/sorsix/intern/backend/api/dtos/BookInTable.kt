package com.sorsix.intern.backend.api.dtos

data class BookInTable(
    var id: Long?,
    var name: String?,
    var isbn: String?,
    var publisher: String?,
    var authors: List<String>?,
    var categories: List<String>?,
    var imgUrl: String?,
    var averageRating: Double?,
    var description: String,
) {
}