package com.moineaufactory.lingvasferoapi.dto

data class CategoryDto(
    val id: Int,
    val textId: String,
    val translations: List<TranslationDto>
)