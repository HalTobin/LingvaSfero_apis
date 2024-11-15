package com.moineaufactory.lingvasferoapi.feature.region

data class AddEditRegionDto(
    val id: Int? = null,
    val languageId: Int,
    val iso: String = "",
    val name: String = "",
    val flagHash: Int = 0,
    val color: Int = 0xFFFFFFFF.toInt(),
    val lightColor: Int = 0xFFFFFFFF.toInt(),
    val darkColor: Int = 0xFF000000.toInt(),
)