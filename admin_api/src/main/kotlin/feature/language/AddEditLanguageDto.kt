package com.moineaufactory.lingvasferoapi.feature.language

import com.moineaufactory.lingvasferoapi.value.SupportLevel

data class AddEditLanguageDto(
    val id: Int? = null,
    val iso: String = "",
    val name: String = "",
    val flagHash: Int = 0,
    val color: Int = 0xFFFFFFFF.toInt(),
    val lightColor: Int = 0xFFFFFFFF.toInt(),
    val darkColor: Int = 0xFF000000.toInt(),
    val supportLevel: Int = SupportLevel.Full.numberId
)