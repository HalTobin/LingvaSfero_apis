package com.moineaufactory.lingvasferoapi.controller

import com.moineaufactory.lingvasferoapi.dto.ContentCreatorDto
import com.moineaufactory.lingvasferoapi.dto.FullContentCreatorDto
import com.moineaufactory.lingvasferoapi.service.ContentCreatorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/content_creator")
class ContentCreatorController @Autowired constructor(
    private val contentCreatorService: ContentCreatorService
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

    @GetMapping("/id/{id}")
    fun getContentCreatorsById(@PathVariable("id") id: Long): ResponseEntity<FullContentCreatorDto?> {
        val creator = contentCreatorService.getFullContentCreator(id)
        return ResponseEntity(creator, creator?.let { HttpStatus.OK } ?: HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @GetMapping("search")
    fun searchContentCreator(@RequestParam("search") search: String): ResponseEntity<List<ContentCreatorDto>> {
        val contentCreators = contentCreatorService.searchContentCreators(search)
        return ResponseEntity(contentCreators, HttpStatus.OK)
    }

}