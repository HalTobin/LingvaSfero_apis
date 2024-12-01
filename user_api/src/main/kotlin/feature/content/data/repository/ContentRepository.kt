package com.moineaufactory.lingvasferoapi.feature.content.data.repository

import com.moineaufactory.lingvasferoapi.feature.content.data.dto.ContentDto

interface ContentRepository {
    suspend fun getContent(channelId: Long, link: String): List<ContentDto>
}