package com.moineaufactory.lingvasferoapi.feature.language.user.controller

import com.moineaufactory.lingvasferoapi.data.dto.CategoryDto
import com.moineaufactory.lingvasferoapi.data.mapper.getCategoryDto
import com.moineaufactory.lingvasferoapi.data.service.CategoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/translation")
class CategoryController @Autowired constructor(
    private val categoryService: CategoryService
) {

    @GetMapping("/all")
    fun getCategories(): ResponseEntity<List<CategoryDto>> {
        val translations = categoryService.getAll().map { it.getCategoryDto() }
        return ResponseEntity(translations, HttpStatus.OK)
    }

}