package com.moineaufactory.lingvasferoapi.data.mapper

import com.moineaufactory.lingvasferoapi.data.dto.TranslationDto
import com.moineaufactory.lingvasferoapi.data.entity.Translation

fun Translation.getTranslationDto() = TranslationDto(
    textId = this.id.textId,
    iso = this.id.iso,
    text = this.text
)