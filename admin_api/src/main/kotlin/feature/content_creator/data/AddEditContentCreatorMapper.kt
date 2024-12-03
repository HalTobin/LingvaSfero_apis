package com.moineaufactory.lingvasferoapi.feature.content_creator.data

import com.moineaufactory.lingvasferoapi.entity.ContentCreator
import com.moineaufactory.lingvasferoapi.feature.content_creator.data.dto.AddEditContentCreatorDto

fun AddEditContentCreatorDto.toEntity() = ContentCreator(
    id = this.id,
    languageId = this.languageId,
    regionId = this.regionId,
    thumbnail = this.thumbnail,
    thumbnailSmall = this.thumbnailSmall,
    name = this.name,
    description = this.description,
    levelMin = this.levelMin,
    levelMax = this.levelMax
)