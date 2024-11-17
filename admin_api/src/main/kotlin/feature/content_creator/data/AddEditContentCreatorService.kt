package com.moineaufactory.lingvasferoapi.feature.content_creator.data

import com.moineaufactory.lingvasferoapi.dto.ContentCreatorDto
import com.moineaufactory.lingvasferoapi.entity.ContentCreator
import com.moineaufactory.lingvasferoapi.entity.ContentCreatorCategory
import com.moineaufactory.lingvasferoapi.entity.ContentCreatorCategoryId
import com.moineaufactory.lingvasferoapi.entity.Region
import com.moineaufactory.lingvasferoapi.mapper.toContentCreatorDto
import com.moineaufactory.lingvasferoapi.repository.ContentCreatorCategoryRepository
import com.moineaufactory.lingvasferoapi.repository.ContentCreatorRepository
import com.moineaufactory.lingvasferoapi.repository.RegionRepository
import com.moineaufactory.lingvasferoapi.service.ContentCreatorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AddEditContentCreatorService @Autowired constructor(
    private val contentCreatorService: ContentCreatorService,
    private val contentCreatorRepository: ContentCreatorRepository,
    private val contentCreatorCategoryRepository: ContentCreatorCategoryRepository
) {

    fun save(addEditContentCreatorDto: AddEditContentCreatorDto): ContentCreatorDto? {
        val addEdit = contentCreatorRepository.save(addEditContentCreatorDto.toEntity())
        return addEdit.id?.let { contentCreatorId ->
            val categories = addEditContentCreatorDto.categoriesId.map { categoryId ->
                ContentCreatorCategory(
                    id = ContentCreatorCategoryId(
                        categoryId = categoryId,
                        contentCreatorId = contentCreatorId
                    )
                )
            }
            contentCreatorCategoryRepository.deleteNotInList(
                contentCreatorId = contentCreatorId,
                newCategoryIds = addEditContentCreatorDto.categoriesId
            )
            contentCreatorCategoryRepository.saveAll(categories)
            return contentCreatorService.getById(contentCreatorId)
        }
    }

}