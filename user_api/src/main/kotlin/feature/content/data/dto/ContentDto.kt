package com.moineaufactory.lingvasferoapi.feature.content.data.dto

data class ContentDto(
    val id: String,
    val channelId: Long,

    val title: String,
    val content: String,
    val thumbnail: String?,
    val thumbnailSmall: String? = null,
    val link: String,

    val timestamp: Long
    //val durationMs: Long // TODO - Maybe for a future iteration?
)