package com.moineaufactory.lingvasferoapi.feature.content.data.dto

data class CacheContentDto(
    val cacheTimestamp: Long,
    val content: List<ContentDto>
)