package com.moineaufactory.lingvasferoapi.data.dto

data class LanguageDto(
    val id: Int,
    val iso: String,
    val name: String,
    val imageUrl: String,
    val imageHash: Int,
    val color: Long,
    val lightColor: Long,
    val darkColor: Long,
    val supportLevel: Int
)