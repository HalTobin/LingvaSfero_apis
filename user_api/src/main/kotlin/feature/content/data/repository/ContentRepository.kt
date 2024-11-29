package com.moineaufactory.lingvasferoapi.feature.content.data.repository

import com.moineaufactory.lingvasferoapi.feature.content.data.dto.ContentDto

interface ContentRepository {
    fun getContent(channelId: Long, link: String): List<ContentDto>
}

class SpotifyContentRepository: ContentRepository {
    override fun getContent(channelId: Long, link: String): List<ContentDto> {
        TODO("Not yet implemented")
    }
}