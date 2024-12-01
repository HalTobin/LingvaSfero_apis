package com.moineaufactory.lingvasferoapi.feature.content.data.repository

import com.moineaufactory.lingvasferoapi.feature.content.data.api.youtube.YoutubeApi
import com.moineaufactory.lingvasferoapi.feature.content.data.dto.ContentDto
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
class YoutubeContentRepository @Autowired constructor(
    private val api: YoutubeApi
): ContentRepository {

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
                            thumbnail = youtubeItem.snippet.thumbnails.thumbnail,
                            thumbnailSmall = youtubeItem.snippet.thumbnails.thumbnailSmall,
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