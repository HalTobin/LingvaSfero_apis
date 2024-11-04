package com.moineaufactory.lingvasferoapi.feature.language.dto

data class LanguageDto(
    val iso: String,
    val name: String,
    val flagUrl: String,
    val flagHash: Int,
    val color: Long,
    val lightColor: Long,
    val darkColor: Long,
    val supportLevel: Int
)