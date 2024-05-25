package com.sorsix.intern.backend.api.dtos

data class CustomerBook(
    val id: Long,
    val customer: UserAvatar,
    val available: Boolean
) {
}