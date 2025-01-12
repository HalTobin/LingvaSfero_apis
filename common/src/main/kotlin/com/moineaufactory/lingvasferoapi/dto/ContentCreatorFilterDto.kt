package com.moineaufactory.lingvasferoapi.dto

data class ContentCreatorFilterDto(
    val languageId: Int? = null,
    val regionId: Int? = null,
    val categoriesId: List<Int> = emptyList(),
    val channelSourceId: Int? = null,
    val levelId: Int? = null
)