package com.sorsix.intern.backend.repository

import com.sorsix.intern.backend.domain.Subscription
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SubscriptionRepository : JpaRepository<Subscription, Long> {
}