package com.sorsix.intern.backend.repository

import com.sorsix.intern.backend.domain.BookInLibrary
import com.sorsix.intern.backend.domain.views.StoreInventory
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface StoreInventoryRepository : JpaRepository<StoreInventory, Long> {
    @Transactional
    @Modifying
    @Query("refresh materialized view stores_inventory", nativeQuery = true)
    fun refreshView()
}