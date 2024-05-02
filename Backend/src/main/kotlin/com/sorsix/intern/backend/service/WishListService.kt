package com.sorsix.intern.backend.service

import com.sorsix.intern.backend.domain.WishList

interface WishListService {
    fun findAllByIdContaining(wishListsId: List<Long>): MutableList<WishList>
}