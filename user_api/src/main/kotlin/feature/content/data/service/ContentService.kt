package com.moineaufactory.lingvasferoapi.feature.content.data.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.moineaufactory.lingvasferoapi.entity.Channel
import com.moineaufactory.lingvasferoapi.feature.content.data.dto.CacheContentDto
import com.moineaufactory.lingvasferoapi.feature.content.data.dto.ContentDto
import com.moineaufactory.lingvasferoapi.feature.content.data.repository.RssContentRepository
import com.moineaufactory.lingvasferoapi.feature.content.data.repository.SpotifyContentRepository
import com.moineaufactory.lingvasferoapi.feature.content.data.repository.YoutubeContentRepository
import com.moineaufactory.lingvasferoapi.value.ChannelSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.File

@Service
class ContentService @Autowired constructor(
    private val rssRepository: RssContentRepository,
    private val youtubeRepository: YoutubeContentRepository,
    private val spotifyRepository: SpotifyContentRepository
) {

    // Create a singleton object mapper to reuse across the application
    private val objectMapper = jacksonObjectMapper().apply {
        registerKotlinModule() // Register Kotlin module for Jackson
    }

    fun getContentByChannel(channel: Channel): List<ContentDto> {
        val source = ChannelSource.findById(channel.sourceId)
        val repository = when (source) {
            ChannelSource.Rss -> rssRepository
            ChannelSource.Youtube -> youtubeRepository
            ChannelSource.Spotify -> spotifyRepository
        }

        channel.id?.let { channelId ->
            val cache = getCachedContent(channelId)
            if (cache != null) {
                if (System.currentTimeMillis() - cache.cacheTimestamp < source.cacheValidity) return cache.content
            } else {
                println("Cache not found: $channelId.json")
                val content = repository.getContent(channelId, channel.sourceLink)
                if (content.isNotEmpty()) writeCache(channelId, content)
                return@getContentByChannel content
            }
        }

        return emptyList()
    }

    // Function to retrieve cached content from a file
    private fun getCachedContent(channelId: Long): CacheContentDto? {
        val cacheFile = File("./cache/channel_content/$channelId.json")
        if (!cacheFile.exists()) return null // Return null if no cache file exists

        println("Read cache: $channelId.json")

        return try {
            objectMapper.readValue(cacheFile.readText())
        } catch (e: Exception) {
            e.printStackTrace()
            null // Return null if deserialization fails
        }
    }

    // Function to write content to cache
    private fun writeCache(channelId: Long, content: List<ContentDto>) {
        val cacheFile = File("./cache/channel/$channelId.json")
        val cacheDir = cacheFile.parentFile

        println("Write cache: $channelId.json")

        if (!cacheDir.exists()) {
            cacheDir.mkdirs() // Create directories if they don’t exist
        }

        val cacheContentDto = CacheContentDto(
            cacheTimestamp = System.currentTimeMillis(),
            content = content
        )

        try {
            objectMapper.writeValue(cacheFile, cacheContentDto) // Serialize to JSON and write to file
        } catch (e: Exception) {
            e.printStackTrace() // Handle any serialization or I/O errors
        }
    }

}