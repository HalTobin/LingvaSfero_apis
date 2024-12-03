package com.moineaufactory.lingvasferoapi.feature.content_creator.data.service

import com.moineaufactory.lingvasferoapi.api.dto.ChannelPreviewDto
import com.moineaufactory.lingvasferoapi.api.repository.RssRepository
import com.moineaufactory.lingvasferoapi.api.repository.YoutubeRepository
import com.moineaufactory.lingvasferoapi.api.repository.SpotifyRepository
import com.moineaufactory.lingvasferoapi.value.ChannelSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RemoteChannelService @Autowired constructor(
    private val rssRepository: RssRepository,
    private val youtubeRepository: YoutubeRepository,
    private val spotifyRepository: SpotifyRepository
) {

    suspend fun searchChannels(search: String, sourceId: Int): List<ChannelPreviewDto> {
        ChannelSource.findByIdOrNull(sourceId)?.let { source ->
            val repository = when (source) {
                ChannelSource.Rss -> rssRepository
                ChannelSource.Youtube -> youtubeRepository
                ChannelSource.Spotify -> spotifyRepository
            }

            return repository.searchChannel(search)
        } ?: return emptyList()
    }

    //suspend fun

}