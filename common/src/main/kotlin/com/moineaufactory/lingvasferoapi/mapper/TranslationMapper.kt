package com.moineaufactory.lingvasferoapi.data.mapper

import com.moineaufactory.lingvasferoapi.dto.TranslationDto
import com.moineaufactory.lingvasferoapi.entity.Translation
import com.moineaufactory.lingvasferoapi.entity.TranslationId

fun Translation.toTranslationDto() = TranslationDto(
    textId = this.id.textId,
    iso = this.id.iso,
    text = this.text
)

fun TranslationDto.toTranslationEntity() = Translation(
    id = TranslationId(
        textId = this.textId,
        iso = this.iso
    ),
    text = this.text
)