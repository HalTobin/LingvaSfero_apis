package com.moineaufactory.lingvasferoapi.feature.content.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CacheContentDto(
    val cacheTimestamp: Long,
    val content: List<ContentDto>
)