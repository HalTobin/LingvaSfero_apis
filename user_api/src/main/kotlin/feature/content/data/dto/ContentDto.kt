package com.moineaufactory.lingvasferoapi.feature.content.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ContentDto(
    val id: String,
    val channelId: Long,

    val title: String,
    val content: String,
    val thumbnail: String,
    val link: String,

    val timestamp: Long
)