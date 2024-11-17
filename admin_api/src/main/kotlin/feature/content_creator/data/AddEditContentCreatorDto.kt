package com.moineaufactory.lingvasferoapi.feature.content_creator.data

data class AddEditContentCreatorDto(
    val id: Long?,

    val languageId: Int,
    val regionId: Int?,

    val thumbnail: String,
    val thumbnailSmall: String?,
    val name: String,
    val description: String,
    val levelMin: Int,
    val levelMax: Int,

    val categoriesId: List<Int>
)