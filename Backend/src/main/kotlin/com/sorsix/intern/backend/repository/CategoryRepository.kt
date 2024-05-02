package com.sorsix.intern.backend.repository

import com.sorsix.intern.backend.domain.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : JpaRepository<Category, Long> {
    fun findAllByIdIn(categoriesId: List<Long>) : MutableList<Category>
}