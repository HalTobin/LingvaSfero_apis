package com.moineaufactory.lingvasferoapi.feature.language.dto

import com.moineaufactory.lingvasferoapi.data.value.SupportLevel

data class AddEditLanguageDto(
    val iso: String = "",
    val name: String = "",
    val flagHash: Int = 0,
    val color: Int = 0xFFFFFFFF.toInt(),
    val lightColor: Int = 0xFFFFFFFF.toInt(),
    val darkColor: Int = 0xFF000000.toInt(),
    val supportLevel: Int = SupportLevel.Full.numberId
)