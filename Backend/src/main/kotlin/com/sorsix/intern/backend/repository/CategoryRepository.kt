package com.sorsix.intern.backend.repository

import com.sorsix.intern.backend.domain.Author
import com.sorsix.intern.backend.domain.Category
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : JpaRepository<Category, Long> {
    fun findAllByIdIn(categoriesId: List<Long>) : MutableList<Category>
    @Query("select c from Category c join PopularCategory pc ON c.id = pc.id")
    fun findPopularCategories(): List<Category>
    @Transactional
    @Modifying
    @Query("refresh materialized view popular_categories", nativeQuery = true)
    fun refreshView()
}