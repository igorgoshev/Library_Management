package com.sorsix.intern.backend.repository

import com.sorsix.intern.backend.domain.Book
import com.sorsix.intern.backend.domain.WishList
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface WishListRepository : JpaRepository<WishList, Long> {
    fun findAllByIdIn(wishListsId: List<Long>) : MutableList<WishList>
    fun findAllByCustomer_Id(customerId: Long) : MutableList<WishList>
    fun findAllByBooksContaining(book: Book) : MutableList<WishList>
}