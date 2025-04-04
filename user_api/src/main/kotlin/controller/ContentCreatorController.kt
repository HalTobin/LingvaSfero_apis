package com.moineaufactory.lingvasferoapi.controller

import com.moineaufactory.lingvasferoapi.dto.ContentCreatorDto
import com.moineaufactory.lingvasferoapi.dto.ContentCreatorFilterDto
import com.moineaufactory.lingvasferoapi.dto.FullContentCreatorDto
import com.moineaufactory.lingvasferoapi.dto.SimpleContentCreatorDto
import com.moineaufactory.lingvasferoapi.service.ContentCreatorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
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
        val contentCreators = contentCreatorService.getAll().sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it.name })
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

    @PutMapping("/filter/how_many")
    fun countContentCreatorsByFilter(
        @RequestBody filter: ContentCreatorFilterDto
    ): ResponseEntity<Int> {
        val nbResult = contentCreatorService.countContentCreatorsByFilter(filter)
        return ResponseEntity(nbResult, HttpStatus.OK)
    }

    /*@PutMapping("/filter")
    fun getContentCreatorsByFilter(
        @RequestBody filter: ContentCreatorFilterDto
    ): ResponseEntity<List<SimpleContentCreatorDto>> {
        val creators = contentCreatorService
            .filterContentCreators(filter)
            .map { SimpleContentCreatorDto(
                id = it.id,
                name = it.name,
                thumbnail = it.thumbnailSmall ?: it.thumbnail
            ) }
        return ResponseEntity(creators, HttpStatus.OK)
    }*/

    @PutMapping("/filter_paged")
    fun getContentCreatorsByFilter(
        @RequestBody filter: ContentCreatorFilterDto,
        p: Pageable
    ): ResponseEntity<Page<SimpleContentCreatorDto>> {
        val creators = contentCreatorService
            .filterContentCreatorsPage(filter, p)
            .map { SimpleContentCreatorDto(
                id = it.id,
                name = it.name,
                thumbnail = it.thumbnailSmall ?: it.thumbnail
            ) }
        return ResponseEntity(creators, HttpStatus.OK)
    }

    @GetMapping("search_paged")
    fun searchContentCreator(
        @RequestParam("search") search: String,
        p: Pageable
    ): ResponseEntity<Page<ContentCreatorDto>> {
        val contentCreators = contentCreatorService.searchContentCreatorsPaged(search, p)
        return ResponseEntity(contentCreators, HttpStatus.OK)
    }

}