package com.moineaufactory.lingvasferoapi.controller

import com.moineaufactory.lingvasferoapi.dto.ContentCreatorFilterDto
import com.moineaufactory.lingvasferoapi.dto.LanguagePreviewDto
import com.moineaufactory.lingvasferoapi.service.ContentCreatorService
import com.moineaufactory.lingvasferoapi.service.LanguageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/public_api/language_preview")
class LanguagePreviewController @Autowired constructor(
    private val languageService: LanguageService,
    private val contentCreatorService: ContentCreatorService
) {

    @GetMapping("/list")
    fun getLanguageListPreview(): ResponseEntity<List<LanguagePreviewDto>> {
        val languages = languageService.getAll().mapNotNull { language ->
            language.id?.let { languageId ->
                LanguagePreviewDto(
                    name = language.name,
                    flag = language.imagePath,
                    color = language.color,
                    supportLevel = language.supportLevel.numberId,
                    nbContent = contentCreatorService
                        .countContentCreatorsByLanguage(languageId = languageId)
                )
            }
        }
        return ResponseEntity(languages, HttpStatus.OK)
    }

}