package com.moineaufactory.lingvasferoapi.feature.language.admin.controller

import com.moineaufactory.lingvasferoapi.data.dto.TranslationDto
import com.moineaufactory.lingvasferoapi.data.mapper.getTranslationDto
import com.moineaufactory.lingvasferoapi.data.service.CategoryService
import com.moineaufactory.lingvasferoapi.feature.language.admin.dto.AddEditCategoryDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin_api/translation")
class AdminCategoryController @Autowired constructor(
    private val categoryService: CategoryService
) {

    @GetMapping("/add")
    fun getCategories(
        @RequestBody category: AddEditCategoryDto
    ): ResponseEntity<List<TranslationDto>> {
        val translations = translationService.getAll().map { it.getTranslationDto() }
        return ResponseEntity(translations, HttpStatus.OK)
    }

}