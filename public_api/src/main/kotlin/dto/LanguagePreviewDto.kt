package com.moineaufactory.lingvasferoapi.dto

data class LanguagePreviewDto(
    val name: String,
    val color: Long,
    val flag: String,
    val supportLevel: Int,
    val nbContent: Int
)