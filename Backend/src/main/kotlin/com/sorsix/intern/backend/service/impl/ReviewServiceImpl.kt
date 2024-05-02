package com.sorsix.intern.backend.service.impl

import com.sorsix.intern.backend.domain.Review
import com.sorsix.intern.backend.repository.ReviewRepository
import com.sorsix.intern.backend.service.ReviewService
import org.springframework.stereotype.Service

@Service
class ReviewServiceImpl(val repository: ReviewRepository) : ReviewService {
    override fun findAllByIdContaining(reviewsId: List<Long>): MutableList<Review> = repository.findAllByIdIn(reviewsId)
}