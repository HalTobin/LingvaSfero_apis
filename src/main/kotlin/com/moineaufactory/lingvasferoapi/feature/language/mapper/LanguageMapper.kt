package com.moineaufactory.lingvasferoapi.feature.language.mapper

import com.moineaufactory.lingvasferoapi.data.model.Language
import com.moineaufactory.lingvasferoapi.data.value.SupportLevel
import com.moineaufactory.lingvasferoapi.feature.language.dto.AddEditLanguageDto
import com.moineaufactory.lingvasferoapi.feature.language.dto.LanguageDto

fun Language.toLanguageDto(): LanguageDto =
    LanguageDto(
        id = this.id!!,
        iso = this.iso,
        name = this.name,
        imageUrl = "/api/language/image?iso=${this.iso}",
        imageHash = this.imageHash,
        color = this.color,
        lightColor = this.lightColor,
        darkColor = this.darkColor,
        supportLevel = this.supportLevel.numberId
    )

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