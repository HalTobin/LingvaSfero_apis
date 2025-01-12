package com.moineaufactory.lingvasferoapi.dto

data class SimpleContentCreatorDto(
    val id: Long,
    val name: String,
    val thumbnail: String?
)

data class ContentCreatorDto(
    val id: Long,
    val languageId: Int,
    val regionId: Int?,
    val thumbnail: String,
    val thumbnailSmall: String?,
    val name: String,
    val description: String,
    val levelMin: Int,
    val levelMax: Int,
    val categoriesId: List<Int>,
    //val channelsId: List<Int>
)

data class FullContentCreatorDto(
    val id: Long,
    val language: LanguageDto,
    val region: RegionDto?,
    val thumbnail: String,
    val thumbnailSmall: String?,
    val name: String,
    val description: String,
    val levelMin: Int,
    val levelMax: Int,
    val categories: List<CategoryDto>,
    //val channels: List<ChanelDto>
)