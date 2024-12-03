package com.moineaufactory.lingvasferoapi.service

import com.moineaufactory.lingvasferoapi.api.repository.RssRepository
import com.moineaufactory.lingvasferoapi.api.repository.SourceRepository
import com.moineaufactory.lingvasferoapi.api.repository.SpotifyRepository
import com.moineaufactory.lingvasferoapi.api.repository.YoutubeRepository
import com.moineaufactory.lingvasferoapi.value.ChannelSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
abstract class ChannelSourceService {

    @Autowired
    lateinit var rssRepository: RssRepository
    @Autowired
    lateinit var youtubeRepository: YoutubeRepository
    @Autowired
    lateinit var spotifyRepository: SpotifyRepository

    fun getRepository(sourceId: Int): SourceRepository? {
        return when (ChannelSource.findByIdOrNull(sourceId)) {
            ChannelSource.Rss -> rssRepository
            ChannelSource.Youtube -> youtubeRepository
            ChannelSource.Spotify -> spotifyRepository
            null -> null
        }
    }

}