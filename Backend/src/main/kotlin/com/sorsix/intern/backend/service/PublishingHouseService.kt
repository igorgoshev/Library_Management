package com.sorsix.intern.backend.service

import com.sorsix.intern.backend.api.dtos.Publisher
import com.sorsix.intern.backend.domain.PublishingHouse

interface PublishingHouseService {
    fun findById(id: Long): PublishingHouse
    fun findAll(): List<PublishingHouse>
    fun deletePublisher(id: Long)
    fun addOrUpdate(publisher: Publisher): PublishingHouse
}