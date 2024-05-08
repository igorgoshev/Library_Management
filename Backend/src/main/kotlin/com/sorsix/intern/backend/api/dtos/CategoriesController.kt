package com.sorsix.intern.backend.api.dtos

import com.sorsix.intern.backend.service.CategoriesService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin
@RequestMapping("/api/categories")
class CategoriesController(
    val categoriesService: CategoriesService
) {
    @GetMapping("")
    fun getCategories() = categoriesService.findAll();
}