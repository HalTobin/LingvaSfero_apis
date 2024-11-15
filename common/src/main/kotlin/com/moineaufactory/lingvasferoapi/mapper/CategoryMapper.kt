package com.moineaufactory.lingvasferoapi.mapper

import com.moineaufactory.lingvasferoapi.dto.CategoryDto
import com.moineaufactory.lingvasferoapi.entity.Category
import com.moineaufactory.lingvasferoapi.entity.Translation
import com.moineaufactory.lingvasferoapi.data.mapper.toTranslationDto

fun Category.getCategoryDto(translations: List<Translation>) = CategoryDto(
    id = this.id!!,
    textId = this.textId,
    translations = translations.map { it.toTranslationDto() }
)