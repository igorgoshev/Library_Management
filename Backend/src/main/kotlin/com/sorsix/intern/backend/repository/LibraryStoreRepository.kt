package com.sorsix.intern.backend.repository

import com.sorsix.intern.backend.domain.LibraryStore
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LibraryStoreRepository : JpaRepository<LibraryStore, Long> {
}