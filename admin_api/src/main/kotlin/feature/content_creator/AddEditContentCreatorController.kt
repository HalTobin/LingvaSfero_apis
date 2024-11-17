package com.moineaufactory.lingvasferoapi.feature.content_creator

import com.moineaufactory.lingvasferoapi.dto.ContentCreatorDto
import com.moineaufactory.lingvasferoapi.feature.content_creator.data.AddEditContentCreatorDto
import com.moineaufactory.lingvasferoapi.feature.content_creator.data.AddEditContentCreatorService
import com.moineaufactory.lingvasferoapi.service.ContentCreatorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin_api/content_creator")
class AddEditContentCreatorController @Autowired constructor(
    private val contentCreatorService: ContentCreatorService,
    private val addEditContentCreatorService: AddEditContentCreatorService,
) {

    @GetMapping("/add_edit")
    fun addEditContentCreator(
        @RequestBody addEditContentCreator: AddEditContentCreatorDto
    ): ResponseEntity<ContentCreatorDto?> {
        val newId = addEditContentCreatorService.save(addEditContentCreator)?.id
        val contentCreator = newId?.let { contentCreatorService.getById(it) }
        return ResponseEntity(contentCreator, contentCreator?.let { HttpStatus.OK } ?: HttpStatus.INTERNAL_SERVER_ERROR)
    }

}