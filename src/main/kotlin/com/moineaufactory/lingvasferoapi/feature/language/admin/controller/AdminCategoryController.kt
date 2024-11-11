package com.moineaufactory.lingvasferoapi.feature.language.admin.controller

import com.moineaufactory.lingvasferoapi.data.dto.CategoryDto
import com.moineaufactory.lingvasferoapi.data.mapper.getCategoryDto
import com.moineaufactory.lingvasferoapi.data.service.CategoryService
import com.moineaufactory.lingvasferoapi.data.service.TranslationService
import com.moineaufactory.lingvasferoapi.feature.language.admin.dto.AddEditCategoryDto
import com.moineaufactory.lingvasferoapi.feature.language.admin.mapper.toCategoryEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin_api/category")
class AdminCategoryController @Autowired constructor(
    private val categoryService: CategoryService,
    private val translationService: TranslationService
) {

    @PostMapping("/add_edit_category")
    fun getCategories(
        @RequestBody category: AddEditCategoryDto
    ): ResponseEntity<List<CategoryDto>> {
        categoryService.save(category.toCategoryEntity())
        val categories = categoryService.getAll().map { it.getCategoryDto(
            translations = translationService.getByTextId(it.textId)
        ) }
        return ResponseEntity(categories, HttpStatus.OK)
    }

}