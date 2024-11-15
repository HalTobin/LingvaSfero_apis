package com.moineaufactory.lingvasferoapi.data.mapper

import com.moineaufactory.lingvasferoapi.entity.Language
import com.moineaufactory.lingvasferoapi.dto.LanguageDto

fun Language.toLanguageDto(): LanguageDto =
    LanguageDto(
        id = this.id!!,
        iso = this.iso,
        name = this.name,
        imageUrl = "api/language/image?iso=${this.iso}",
        imageHash = this.imageHash,
        color = this.color,
        lightColor = this.lightColor,
        darkColor = this.darkColor,
        supportLevel = this.supportLevel.numberId
    )