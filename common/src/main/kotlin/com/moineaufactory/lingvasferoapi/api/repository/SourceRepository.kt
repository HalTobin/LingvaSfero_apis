package com.moineaufactory.lingvasferoapi.api.repository

import com.moineaufactory.lingvasferoapi.api.dto.ChannelDetailsDto
import com.moineaufactory.lingvasferoapi.api.dto.ChannelPreviewDto
import com.moineaufactory.lingvasferoapi.dto.ContentDto

interface SourceRepository {
    suspend fun getContent(channelId: Long, link: String): List<ContentDto>
    suspend fun searchChannel(search: String): List<ChannelPreviewDto>
    suspend fun getChannelDetails(id: String): ChannelDetailsDto?
}