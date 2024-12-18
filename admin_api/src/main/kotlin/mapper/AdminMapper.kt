package com.moineaufactory.lingvasferoapi.mapper

import com.moineaufactory.lingvasferoapi.entity.Category
import com.moineaufactory.lingvasferoapi.entity.Channel
import com.moineaufactory.lingvasferoapi.entity.Language
import com.moineaufactory.lingvasferoapi.value.SupportLevel
import com.moineaufactory.lingvasferoapi.feature.category.AddEditCategoryDto
import com.moineaufactory.lingvasferoapi.feature.language.AddEditLanguageDto
import com.moineaufactory.lingvasferoapi.feature.region.AddEditRegionDto
import com.moineaufactory.lingvasferoapi.entity.Region
import com.moineaufactory.lingvasferoapi.feature.content_creator.data.dto.AddEditChannelDto
import com.moineaufactory.lingvasferoapi.value.ChannelSource

fun AddEditLanguageDto.toLanguageEntity(): Language =
    Language(
        id = this.id,
        iso = this.iso,
        name = this.name,
        imagePath = "./img/languages/${this.iso}.png",
        imageHash = this.flagHash,
        color = this.color.toLong(),
        lightColor = this.lightColor.toLong(),
        darkColor = this.darkColor.toLong(),
        supportLevel = SupportLevel.entries.find { it.numberId == this.supportLevel } ?: SupportLevel.Full
    )

fun AddEditRegionDto.toRegionEntity(): Region =
    Region(
        id = this.id,
        languageId = this.languageId,
        iso = this.iso,
        name = this.name,
        imagePath = "./img/regions/${this.iso}.png",
        imageHash = this.flagHash,
        color = this.color.toLong(),
        lightColor = this.lightColor.toLong(),
        darkColor = this.darkColor.toLong()
    )

fun AddEditCategoryDto.toCategoryEntity(): Category =
    Category(
        id = this.id,
        textId = this.textId
    )

fun AddEditChannelDto.toChannelEntity(): Channel =
    Channel(
        id = this.id,
        contentCreatorId = contentCreatorId,
        title = this.title.ifBlank { null },
        description = this.description.ifBlank { null },
        sourceId = ChannelSource.findById(this.sourceId).id,
        sourceLink = this.sourceLink,
        website = this.website,
        contentDate = 0
    )