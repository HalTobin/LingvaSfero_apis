package com.moineaufactory.lingvasferoapi.controller

import com.moineaufactory.lingvasferoapi.dto.ContentCreatorDto
import com.moineaufactory.lingvasferoapi.dto.RegionDto
import com.moineaufactory.lingvasferoapi.mapper.toContentCreatorDto
import com.moineaufactory.lingvasferoapi.mapper.toRegionDto
import com.moineaufactory.lingvasferoapi.repository.ContentCreatorCategoryRepository
import com.moineaufactory.lingvasferoapi.service.ContentCreatorService
import com.moineaufactory.lingvasferoapi.service.RegionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.io.File
import java.io.IOException
import java.util.*

@RestController
@RequestMapping("/api/content_creator")
class ContentCreatorController @Autowired constructor(
    private val contentCreatorService: ContentCreatorService,
    private val contentCreatorCategoryRepository: ContentCreatorCategoryRepository
) {

    @GetMapping("/all")
    fun getContentCreators(): ResponseEntity<List<ContentCreatorDto>> {
        val contentCreators = contentCreatorService.getAll()
        return ResponseEntity(contentCreators, HttpStatus.OK)
    }

    @GetMapping("/find")
    fun getContentCreatorsByLanguageId(@RequestParam("language_id") languageId: Int): ResponseEntity<List<ContentCreatorDto>> {
        val contentCreators = contentCreatorService.getByLanguageId(languageId)
        return ResponseEntity(contentCreators, HttpStatus.OK)
    }

}