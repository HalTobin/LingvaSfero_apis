package com.moineaufactory.lingvasferoapi.data.mapper

import com.moineaufactory.lingvasferoapi.data.dto.CategoryDto
import com.moineaufactory.lingvasferoapi.data.entity.Category
import com.moineaufactory.lingvasferoapi.data.entity.Translation

fun Category.getCategoryDto(translations: List<Translation>) = CategoryDto(
    id = this.id!!,
    textId = this.textId,
    translations = translations.map { it.getTranslationDto() }
)