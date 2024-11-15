package com.moineaufactory.lingvasferoapi.feature.translation

import com.moineaufactory.lingvasferoapi.dto.TranslationDto
import com.moineaufactory.lingvasferoapi.data.mapper.toTranslationDto
import com.moineaufactory.lingvasferoapi.service.TranslationService
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
        val translations = translationService.getAll().map { it.toTranslationDto() }
        return ResponseEntity(translations, HttpStatus.OK)
    }

}