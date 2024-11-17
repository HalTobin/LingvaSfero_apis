package com.moineaufactory.lingvasferoapi.service

import com.moineaufactory.lingvasferoapi.dto.ContentCreatorDto
import com.moineaufactory.lingvasferoapi.entity.ContentCreator
import com.moineaufactory.lingvasferoapi.entity.Region
import com.moineaufactory.lingvasferoapi.mapper.toContentCreatorDto
import com.moineaufactory.lingvasferoapi.repository.ContentCreatorCategoryRepository
import com.moineaufactory.lingvasferoapi.repository.ContentCreatorRepository
import com.moineaufactory.lingvasferoapi.repository.RegionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ContentCreatorService @Autowired constructor(
    private val contentCreatorRepository: ContentCreatorRepository,
    private val contentCreatorCategoryRepository: ContentCreatorCategoryRepository
) {
    fun getAll(): List<ContentCreatorDto> = contentCreatorRepository.findAll().mapNotNull { it.withCategories() }
    fun getById(id: Long): ContentCreatorDto? = contentCreatorRepository.findById(id).orElse(null)?.withCategories()
    fun getByLanguageId(languageId: Int): List<ContentCreatorDto> = contentCreatorRepository.findByLanguageId(languageId).mapNotNull { it.withCategories()}

    fun ContentCreator.withCategories(): ContentCreatorDto? {
        return this.id?.let { contentCreatorId ->
            val categories = contentCreatorCategoryRepository.findByContentCreatorId(contentCreatorId)
            this.toContentCreatorDto(categories.map { it.id.categoryId })
        }
    }
}