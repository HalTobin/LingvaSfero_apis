package com.moineaufactory.lingvasferoapi.api.repository

import com.moineaufactory.lingvasferoapi.api.dto.ChannelDetailsDto
import com.moineaufactory.lingvasferoapi.api.dto.ChannelPreviewDto
import com.moineaufactory.lingvasferoapi.api.youtube.YoutubeApi
import com.moineaufactory.lingvasferoapi.dto.ContentDto
import com.moineaufactory.lingvasferoapi.mapper.toChannelSourceDto
import com.moineaufactory.lingvasferoapi.value.ChannelSource
import okhttp3.OkHttpClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Service
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

@Service
class YoutubeRepository @Autowired constructor(
    private val api: YoutubeApi
): SourceRepository {

    @Value("\${youtube.api.key}")
    private lateinit var apiKey: String

    override suspend fun getContent(channelId: Long, link: String): List<ContentDto> {
        try {
            val response = api.getVideos(
                key = apiKey,
                playlistId = link
            ).awaitResponse()
            if (response.isSuccessful) {
                return response.body()?.let { youtubeApiResponse ->
                    youtubeApiResponse.items.map { youtubeItem ->
                        ContentDto(
                            id = "${channelId}_${youtubeItem.snippet.resourceId.videoId}",
                            channelId = channelId,
                            title = youtubeItem.snippet.title,
                            content = youtubeItem.snippet.description,
                            thumbnail = youtubeItem.snippet.thumbnails.getThumbnail(),
                            thumbnailSmall = youtubeItem.snippet.thumbnails.getThumbnailSmall(),
                            link = "https://www.youtube.com/watch?v=${youtubeItem.snippet.resourceId.videoId}",
                            timestamp = youtubeItem.snippet.publishedAtToEpochMilli()
                        )
                    }
                } ?: emptyList()
            } else return emptyList()
        } catch (e: Exception) {
            println("Couldn't reach Youtube API")
            e.printStackTrace()
            return emptyList()
        }
    }

    override suspend fun searchChannel(search: String): List<ChannelPreviewDto> {
        try {
            val response = api.getChannelsPreview(
                key = apiKey,
                search = search
            ).awaitResponse()
            return if (response.isSuccessful) {
                response.body()?.let { youtubeApiResponse ->
                    youtubeApiResponse.items.map { youtubeItem ->
                        ChannelPreviewDto(
                            channelId = youtubeItem.snippet.channelId,
                            title = youtubeItem.snippet.title,
                            description = youtubeItem.snippet.description,
                            image = youtubeItem.snippet.thumbnails.getThumbnail()
                        )
                    }
                } ?: emptyList()
            } else emptyList()
        } catch (e: Exception) {
            e.printStackTrace()
            return emptyList()
        }
    }

    override suspend fun getChannelDetails(id: String): ChannelDetailsDto? {
        try {
            val response = api.getChannelDetails(
                key = apiKey,
                channelId = id
            ).awaitResponse()
            return if (response.isSuccessful) {
                response.body()?.let { youtubeApiResponse ->
                    youtubeApiResponse.items.firstOrNull()?.let { youtubeItem ->
                        return ChannelDetailsDto(
                            channelId = id.replaceRange(1, 2, "U"),
                            title = youtubeItem.snippet.title,
                            description = youtubeItem.snippet.description,
                            source = ChannelSource.Youtube.toChannelSourceDto(),
                            website = "https://youtube.com/${youtubeItem.snippet.customUrl}",
                            thumbnail = youtubeItem.snippet.thumbnails.getThumbnail(),
                            thumbnailSmall = youtubeItem.snippet.thumbnails.getThumbnailSmall()
                        )
                    }
                }
            } else null
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

}

object Youtube {
    const val API_URL = "https://www.googleapis.com/youtube/v3/"
}

@Configuration
class YoutubeApiConfig {

    @Bean
    fun youtubeApi(okHttpClient: OkHttpClient): YoutubeApi {
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Youtube.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(YoutubeApi::class.java)
    }
    
}