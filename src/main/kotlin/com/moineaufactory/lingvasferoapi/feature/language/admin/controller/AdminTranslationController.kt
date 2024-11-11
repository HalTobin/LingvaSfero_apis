package com.moineaufactory.lingvasferoapi.feature.language.admin.controller

import com.moineaufactory.lingvasferoapi.data.dto.TranslationDto
import com.moineaufactory.lingvasferoapi.data.mapper.getCategoryDto
import com.moineaufactory.lingvasferoapi.data.mapper.toTranslationEntity
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
@RequestMapping("/admin_api/translation")
class AdminTranslationController @Autowired constructor(
    private val translationService: TranslationService
) {

    @PostMapping("/add_edit_translation")
    fun getTranslations(
        @RequestBody translation: TranslationDto
    ): ResponseEntity<Boolean> {
        translationService.save(translation.toTranslationEntity())
        return ResponseEntity(true, HttpStatus.OK)
    }

}