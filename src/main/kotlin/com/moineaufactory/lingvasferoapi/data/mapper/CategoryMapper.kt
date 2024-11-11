package com.moineaufactory.lingvasferoapi.data.mapper

import com.moineaufactory.lingvasferoapi.data.dto.CategoryDto
import com.moineaufactory.lingvasferoapi.data.entity.Category

fun Category.getCategoryDto() = CategoryDto(
    id = this.id,
    textId = this.textId,
    translations = this.translations.map { it.getTranslationDto() }
)