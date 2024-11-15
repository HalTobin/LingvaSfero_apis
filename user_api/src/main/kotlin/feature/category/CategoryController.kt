package com.moineaufactory.lingvasferoapi.feature.category

import com.moineaufactory.lingvasferoapi.dto.CategoryDto
import com.moineaufactory.lingvasferoapi.mapper.getCategoryDto
import com.moineaufactory.lingvasferoapi.service.CategoryService
import com.moineaufactory.lingvasferoapi.service.TranslationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/category")
class CategoryController @Autowired constructor(
    private val categoryService: CategoryService,
    private val translationService: TranslationService
) {

    @GetMapping("/all")
    fun getCategories(): ResponseEntity<List<CategoryDto>> {
        val categories = categoryService.getAll().map { it.getCategoryDto(
            translations = translationService.getByTextId(it.textId)
        ) }
        return ResponseEntity(categories, HttpStatus.OK)
    }

    // Get to /iso/:id that returns a language by id
    @GetMapping("/id/{id}")
    fun getCategory(@PathVariable id: Int?): ResponseEntity<CategoryDto?> = id?.let {
        return@let categoryService.getById(id)?.let { category ->
            val categoryDto = category.getCategoryDto(
                translations = translationService.getByTextId(category.textId)
            )
            ResponseEntity(categoryDto, HttpStatus.OK)
        }
    } ?: ResponseEntity(null, HttpStatus.BAD_REQUEST)

}