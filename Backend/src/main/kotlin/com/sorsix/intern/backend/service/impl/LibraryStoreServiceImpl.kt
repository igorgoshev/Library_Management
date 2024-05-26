package com.sorsix.intern.backend.service.impl

import com.sorsix.intern.backend.api.dtos.StoreDetails
import com.sorsix.intern.backend.domain.LibraryStore
import com.sorsix.intern.backend.domain.dto.LibraryStoreDto
import com.sorsix.intern.backend.repository.LibrarianRepository
import com.sorsix.intern.backend.repository.LibraryStoreRepository
import com.sorsix.intern.backend.service.BookInLibraryService
import com.sorsix.intern.backend.service.LibraryService
import com.sorsix.intern.backend.service.LibraryStoreService
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
@Service
class LibraryStoreServiceImpl(
    private val repository: LibraryStoreRepository,
    private val libraryService: LibraryService,
    private val bookInLibraryService: BookInLibraryService,
    private val librarianRepository: LibrarianRepository,
    private val uploadService: FileUploadService
) : LibraryStoreService {

    @Value("\${upload.path}")
    private lateinit var uploadPath: String

    override fun findById(id: Long?): LibraryStore? = id?.let { repository.findById(it).get() }

    override fun findAll(): List<LibraryStore> = repository.findAll();

    override fun delete(id: Long): LibraryStore? {
        return findById(id)?.let {
            repository.delete(it)
            it
        }
    }

    override fun findAllDto(): List<com.sorsix.intern.backend.api.dtos.LibraryStore> {
        return repository.findAll()
            .map { com.sorsix.intern.backend.api.dtos.LibraryStore(
                id = it.id ?: 0,
                name = it.name,
                imgUrl = it.imgUrl,
                libraryName = it.library?.name ?: "",
                address = it.address
            ) }
    }

    override fun save(id: Long?, libraryStoreDto: LibraryStoreDto) : LibraryStore? =
        findById(id)?.let {
            it.library = libraryService.findById(libraryStoreDto.libraryId)
            it.name = libraryStoreDto.name
            it.address = libraryStoreDto.address
            it.imgUrl = libraryStoreDto.imgUrl
            it.bookInLibrary = bookInLibraryService.findAllByIdContaining(libraryStoreDto.bookInLibrariesId);
            repository.save(it)
        } ?: repository.save(
            LibraryStore(
                id = 0,
                address = libraryStoreDto.address,
                name = libraryStoreDto.name,
                imgUrl = libraryStoreDto.imgUrl,
                library = libraryService.findById(libraryStoreDto.libraryId),
                bookInLibrary = bookInLibraryService.findAllByIdContaining(libraryStoreDto.bookInLibrariesId))
        )

    override fun getPopularStores(): List<StoreDetails> {
        return repository.findPopularStores().map {
            StoreDetails(
                name = it.name,
                id = it.id ?: 0L,
                address = it.address,
                libraryName = it.library?.name ?: ""
            )
        }
    }

    override fun addOrUpdate(libraryStore: com.sorsix.intern.backend.api.dtos.LibraryStore,
                             imgFile: MultipartFile,
                             userId: Long): LibraryStore {
        val fileName = uploadService.saveImage(imgFile)
        val store = repository.findByIdOrNull(libraryStore.id) ?: LibraryStore()
        val library = librarianRepository.findLibraryByUserId(userId) ?: throw ChangeSetPersister.NotFoundException()
        store.name = libraryStore.name
        store.address = libraryStore.address
        store.imgUrl = "/uploads/$fileName"
        store.library = library
        return repository.save(store)
    }
}

