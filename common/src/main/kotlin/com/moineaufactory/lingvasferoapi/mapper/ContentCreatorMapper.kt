package com.moineaufactory.lingvasferoapi.mapper

import com.moineaufactory.lingvasferoapi.dto.ContentCreatorDto
import com.moineaufactory.lingvasferoapi.entity.ContentCreator

fun ContentCreator.toContentCreatorDto(
    categoriesId: List<Int>
) = ContentCreatorDto(
    id = this.id!!,
    languageId = this.languageId,
    regionId = this.regionId,
    thumbnail = this.thumbnail,
    thumbnailSmall = this.thumbnailSmall,
    name = this.name,
    description = this.description,
    levelMin = this.levelMin,
    levelMax = this.levelMax,
    categoriesId = categoriesId
)