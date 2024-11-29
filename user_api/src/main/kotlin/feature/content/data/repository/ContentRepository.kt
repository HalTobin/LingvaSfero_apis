package com.moineaufactory.lingvasferoapi.feature.content.data.repository

import com.moineaufactory.lingvasferoapi.feature.content.data.dto.ContentDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

interface ContentRepository {
    fun getContent(channelId: Long, link: String): List<ContentDto>
}

@Service
class SpotifyContentRepository @Autowired constructor(): ContentRepository {
    override fun getContent(channelId: Long, link: String): List<ContentDto> {
        TODO("Not yet implemented")
    }
}