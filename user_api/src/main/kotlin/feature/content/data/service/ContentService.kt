package com.moineaufactory.lingvasferoapi.feature.content.data.service

import com.moineaufactory.lingvasferoapi.entity.Channel
import com.moineaufactory.lingvasferoapi.feature.content.data.dto.CacheContentDto
import com.moineaufactory.lingvasferoapi.feature.content.data.dto.ContentDto
import com.moineaufactory.lingvasferoapi.feature.content.data.repository.RssContentRepository
import com.moineaufactory.lingvasferoapi.feature.content.data.repository.SpotifyContentRepository
import com.moineaufactory.lingvasferoapi.feature.content.data.repository.YoutubeContentRepository
import com.moineaufactory.lingvasferoapi.value.ChannelSource
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.File

@Service
class ContentService @Autowired constructor() {

    // TODO - Implement cache
    fun getContentByChannel(channel: Channel): List<ContentDto> {
        val source = ChannelSource.findById(channel.sourceId)
        val repository = when (source) {
            ChannelSource.Rss -> RssContentRepository()
            ChannelSource.Youtube -> YoutubeContentRepository()
            ChannelSource.Spotify -> SpotifyContentRepository()
        }

        channel.id?.let { channelId ->
            getCachedContent(channelId)?.let { cache ->
                if (System.currentTimeMillis() - cache.cacheTimestamp < source.cacheValidity) return cache.content
            }
            println("Cache not found: $channelId.json")
            val content = repository.getContent(channelId, channel.sourceLink)
            if (content.isNotEmpty()) writeCache(channelId, content)
            return content
        }

        return emptyList()
    }

    // Function to retrieve cached content from a file
    private fun getCachedContent(channelId: Long): CacheContentDto? {
        val cacheFile = File("./cache/channel/$channelId.json")
        if (!cacheFile.exists()) return null // Return null if no cache file exists

        println("Read cache: $channelId.json")

        return try {
            val cacheJson = cacheFile.readText()
            Json.decodeFromString<CacheContentDto>(cacheJson)
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
            val cacheJson = Json.encodeToString(cacheContentDto)
            cacheFile.writeText(cacheJson) // Write JSON to file
        } catch (e: Exception) {
            e.printStackTrace() // Handle any serialization or I/O errors
        }
    }

}