package com.sorsix.intern.backend.service.impl

import com.sorsix.intern.backend.domain.WishList
import com.sorsix.intern.backend.repository.WishListRepository
import com.sorsix.intern.backend.service.WishListService
import org.springframework.stereotype.Service

@Service
class WishListServiceImpl(val repository: WishListRepository) : WishListService {
    override fun findAllByIdContaining(wishListsId: List<Long>): MutableList<WishList> = repository.findAllByIdIn(wishListsId);
}