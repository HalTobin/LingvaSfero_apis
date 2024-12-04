package com.moineaufactory.lingvasferoapi.feature.content_creator.data.service

import com.moineaufactory.lingvasferoapi.api.dto.ChannelDetailsDto
import com.moineaufactory.lingvasferoapi.api.dto.ChannelPreviewDto
import com.moineaufactory.lingvasferoapi.api.repository.RssRepository
import com.moineaufactory.lingvasferoapi.api.repository.YoutubeRepository
import com.moineaufactory.lingvasferoapi.api.repository.SpotifyRepository
import com.moineaufactory.lingvasferoapi.service.ChannelSourceService
import com.moineaufactory.lingvasferoapi.value.ChannelSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RemoteChannelService @Autowired constructor(): ChannelSourceService() {

    suspend fun searchChannels(search: String, sourceId: Int): List<ChannelPreviewDto> =
        getRepository(sourceId)?.searchChannel(search) ?: emptyList()

    suspend fun getChannelDetails(channelId: String, sourceId: Int): ChannelDetailsDto? =
        getRepository(sourceId)?.getChannelDetails(channelId)

}