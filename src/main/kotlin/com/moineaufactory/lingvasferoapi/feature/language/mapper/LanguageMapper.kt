package com.moineaufactory.lingvasferoapi.feature.language.mapper

import com.moineaufactory.lingvasferoapi.data.model.Language
import com.moineaufactory.lingvasferoapi.data.value.SupportLevel
import com.moineaufactory.lingvasferoapi.feature.language.dto.AddEditLanguageDto
import com.moineaufactory.lingvasferoapi.feature.language.dto.LanguageDto

fun Language.toLanguageDto(): LanguageDto =
    LanguageDto(
        iso = this.iso,
        name = this.name,
        flagUrl = "/api/language/image?iso=${this.iso}",
        flagHash = this.flagHash,
        color = this.color,
        lightColor = this.lightColor,
        darkColor = this.darkColor,
        supportLevel = this.supportLevel.numberId
    )

fun AddEditLanguageDto.toLanguageEntity(): Language =
    Language(
        iso = this.iso,
        name = this.name,
        flagUrl = "./img/languages/${this.iso}.png",
        flagHash = this.flagHash,
        color = this.color.toLong(),
        lightColor = this.lightColor.toLong(),
        darkColor = this.darkColor.toLong(),
        supportLevel = SupportLevel.entries.find { it.numberId == this.supportLevel } ?: SupportLevel.Full
    )