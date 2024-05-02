package com.sorsix.intern.backend.service

import com.sorsix.intern.backend.domain.Review

interface ReviewService {
    fun findAllByIdContaining(reviewsId: List<Long>) : MutableList<Review>
}