package com.sorsix.intern.backend.api

import com.sorsix.intern.backend.api.dtos.Publisher
import com.sorsix.intern.backend.config.CurrentUser
import com.sorsix.intern.backend.domain.PublishingHouse
import com.sorsix.intern.backend.service.PublishingHouseService
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequestMapping("/api/publishers")
class PublishingHousesController(
    val publishingHouseService: PublishingHouseService
) {
    @GetMapping("")
    fun getPublishers() = publishingHouseService.findAll()

    @DeleteMapping("/{id}")
    fun deletePublisher(@PathVariable id: Long) {
        publishingHouseService.deletePublisher(id);
    }

    @PostMapping("")
    fun addOrUpdatePublisher(@RequestBody publisher: Publisher): PublishingHouse {
        return publishingHouseService.addOrUpdate(publisher);
    }
}