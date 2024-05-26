package com.sorsix.intern.backend.api

import com.sorsix.intern.backend.api.dtos.LibraryStore
import com.sorsix.intern.backend.api.dtos.LibraryStoreWithImage
import com.sorsix.intern.backend.api.dtos.StoreDetails
import com.sorsix.intern.backend.config.CurrentUser
import com.sorsix.intern.backend.security.UserPrincipal
import com.sorsix.intern.backend.service.impl.LibraryStoreServiceImpl
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

@RestController
@CrossOrigin
@RequestMapping("/api/libraries")
class LibrariesController(private val libraryStoreServiceImpl: LibraryStoreServiceImpl) {

    @GetMapping("/stores/popular")
    fun getPopularStores(): List<StoreDetails> {
        return libraryStoreServiceImpl.getPopularStores()
    }

    @GetMapping("/stores")
    fun getStores(): List<LibraryStore> {
        return libraryStoreServiceImpl.findAllDto()
    }

    @PostMapping("/stores", consumes = ["multipart/form-data"])
    fun addStore(@RequestPart("store") libraryStore: LibraryStore,
                 @RequestPart("imgFile") imgFile: MultipartFile,
                 @CurrentUser userPrincipal: UserPrincipal) {
        libraryStoreServiceImpl.addOrUpdate(libraryStore, imgFile, userPrincipal.id)
        println()
    }


}