package com.moineaufactory.lingvasferoapi.feature.language.user.controller

import com.moineaufactory.lingvasferoapi.data.dto.TranslationDto
import com.moineaufactory.lingvasferoapi.data.mapper.getTranslationDto
import com.moineaufactory.lingvasferoapi.data.service.TranslationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/translation")
class TranslationController @Autowired constructor(
    private val translationService: TranslationService
) {

    @GetMapping("/all")
    fun getTranslations(): ResponseEntity<List<TranslationDto>> {
        val translations = translationService.getAll().map { it.getTranslationDto() }
        return ResponseEntity(translations, HttpStatus.OK)
    }

}