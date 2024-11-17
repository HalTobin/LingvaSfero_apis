package com.moineaufactory.lingvasferoapi.dto

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
