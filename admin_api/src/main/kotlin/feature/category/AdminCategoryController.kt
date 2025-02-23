package com.moineaufactory.lingvasferoapi.feature.category

import com.moineaufactory.lingvasferoapi.dto.CategoryDto
import com.moineaufactory.lingvasferoapi.mapper.getCategoryDto
import com.moineaufactory.lingvasferoapi.service.CategoryService
import com.moineaufactory.lingvasferoapi.service.TranslationService
import com.moineaufactory.lingvasferoapi.mapper.toCategoryEntity
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
        val oldCategory = category.id?.let { categoryService.getById(it) }

        if (oldCategory != null && oldCategory.textId != category.textId)
            translationService.updateTextIds(oldCategory.textId, category.textId)

        categoryService.save(category.toCategoryEntity())
        val categories = categoryService.getAll().map { it.getCategoryDto(
            translations = translationService.getByTextId(it.textId)
        ) }
        return ResponseEntity(categories, HttpStatus.OK)
    }

}