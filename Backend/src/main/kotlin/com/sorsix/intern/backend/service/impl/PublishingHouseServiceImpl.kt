package com.sorsix.intern.backend.service.impl

import com.sorsix.intern.backend.api.dtos.Publisher
import com.sorsix.intern.backend.domain.PublishingHouse
import com.sorsix.intern.backend.repository.PublishingHouseRepository
import com.sorsix.intern.backend.service.PublishingHouseService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PublishingHouseServiceImpl(val repository: PublishingHouseRepository) : PublishingHouseService {
    override fun findById(id: Long): PublishingHouse = repository.findById(id).get()
    override fun findAll(): List<PublishingHouse> = repository.findAll()
    override fun deletePublisher(id: Long) = repository.deleteById(id)
    override fun addOrUpdate(publisher: Publisher): PublishingHouse {
        val publishingHouse = repository.findByIdOrNull(publisher.id) ?: PublishingHouse();
        publishingHouse.name = publisher.name
        publishingHouse.address = publisher.address
        publishingHouse.id = publisher.id
        return repository.save(publishingHouse)
    }
}