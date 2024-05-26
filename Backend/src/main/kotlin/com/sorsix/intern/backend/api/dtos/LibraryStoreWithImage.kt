package com.sorsix.intern.backend.api.dtos

import org.springframework.web.multipart.MultipartFile

data class LibraryStoreWithImage(
    val id: Long = 0,
    val address: String = "",
    val name: String = "",
    val imgUrl: String = "",
    var libraryName: String = "",
    var imgFile: MultipartFile? = null,
) {
}