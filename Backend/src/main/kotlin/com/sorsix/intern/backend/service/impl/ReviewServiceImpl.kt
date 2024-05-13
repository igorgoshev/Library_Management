package com.sorsix.intern.backend.service.impl

import com.sorsix.intern.backend.api.dtos.AddReview
import com.sorsix.intern.backend.domain.Book
import com.sorsix.intern.backend.domain.Review
import com.sorsix.intern.backend.repository.ReviewRepository
import com.sorsix.intern.backend.service.ReviewService
import com.sorsix.intern.backend.service.UserService
import org.springframework.stereotype.Service

@Service
class ReviewServiceImpl(
    val repository: ReviewRepository,
    val bookRepository: BookServiceImpl,
    val userService: UserService) : ReviewService {
    override fun findAllByIdContaining(reviewsId: List<Long>): MutableList<Review> = repository.findAllByIdIn(reviewsId)
    override fun createReview(bookId: Long, review: AddReview): Unit {
        var book: Book? = bookRepository.findById(bookId)
        val newReview: Review = Review(id = null, dateReviewed = review.dateReviewed, description = review.description, rate = review.value, customer =  userService.findById(review.userId), book = book)

    }

}