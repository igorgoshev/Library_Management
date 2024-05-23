package com.sorsix.intern.backend.repository

import com.sorsix.intern.backend.domain.Author
import com.sorsix.intern.backend.domain.LibraryStore
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface LibraryStoreRepository : JpaRepository<LibraryStore, Long> {
    @Query("select ls from LibraryStore ls join PopularStore ps ON ls.id = ps.id")
    fun findPopularStores(): List<LibraryStore>
    @Transactional
    @Modifying
    @Query("refresh materialized view popular_stores", nativeQuery = true)
    fun refreshView()
}