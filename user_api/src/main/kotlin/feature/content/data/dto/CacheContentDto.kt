package com.moineaufactory.lingvasferoapi.feature.content.data.dto

import com.moineaufactory.lingvasferoapi.dto.ContentDto

data class CacheContentDto(
    val cacheTimestamp: Long,
    val content: List<ContentDto>
)