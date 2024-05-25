package com.sorsix.intern.backend.api.dtos

data class CustomerBookCard(
    var id: Long,
    var name: String,
    var authors: List<String>?,
    var imgUrl: String,
    var available: Boolean,
) {}