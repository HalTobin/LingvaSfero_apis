package com.moineaufactory.lingvasferoapi.api.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.moineaufactory.lingvasferoapi.api.spotify.SpotifyApi
import com.moineaufactory.lingvasferoapi.api.SpotifyAuthInterceptor
import com.moineaufactory.lingvasferoapi.api.dto.ChannelDetailsDto
import com.moineaufactory.lingvasferoapi.api.dto.ChannelPreviewDto
import com.moineaufactory.lingvasferoapi.dto.ContentDto
import com.moineaufactory.lingvasferoapi.mapper.toChannelSourceDto
import com.moineaufactory.lingvasferoapi.value.ChannelSource
import okhttp3.OkHttpClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Service
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

@Service
class SpotifyRepository @Autowired constructor(
    private val api: SpotifyApi
): SourceRepository {

    override suspend fun getContent(channelId: Long, link: String): List<ContentDto> {
        try {
            val response = api.getEpisodes(showId = link)
                .awaitResponse()
            return if (response.isSuccessful) {
                response.body()?.let { spotifyApiResponse ->
                    spotifyApiResponse.items.map { spotifyItem ->
                        ContentDto(
                            id = "${channelId}_${spotifyItem.id}",
                            channelId = channelId,
                            title = spotifyItem.name,
                            content = spotifyItem.description,
                            thumbnail = spotifyItem.getThumbnail(),
                            thumbnailSmall = spotifyItem.getThumbnailSmall(),
                            link = "https://open.spotify.com/episode/${spotifyItem.id}",
                            timestamp = spotifyItem.releaseDateToEpochMilli()
                        )
                    }
                } ?: emptyList()
            } else emptyList()
        } catch (e: Exception) {
            println("Couldn't reach Spotify API")
            e.printStackTrace()
            return emptyList()
        }
    }

    override suspend fun searchChannel(search: String): List<ChannelPreviewDto> {
        try {
            val response = api.getPodcastsPreview(search)
                .awaitResponse()
            return if (response.isSuccessful) {
                response.body()?.let { spotifyApiResponse ->
                    spotifyApiResponse.shows.items.map { spotifyItem ->
                        ChannelPreviewDto(
                            channelId = spotifyItem.id,
                            title = spotifyItem.name,
                            description = spotifyItem.description,
                            image = spotifyItem.getThumbnail()
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
            val response = api.getPodcastDetails(id)
                .awaitResponse()
            return if (response.isSuccessful) {
                response.body()?.let { spotifyItem ->
                    ChannelDetailsDto(
                        channelId = id,
                        title = spotifyItem.name,
                        description = spotifyItem.description,
                        source = ChannelSource.Spotify.toChannelSourceDto(),
                        website = spotifyItem.externalUrls.spotify,
                        thumbnail = spotifyItem.getThumbnail(),
                        thumbnailSmall = spotifyItem.getThumbnailSmall()
                    )
                }
            } else null
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

}

object Spotify {
    const val API_URL = "https://api.spotify.com/v1/"
}

@Configuration
class SpotifyApiConfig(
    private val authInterceptor: SpotifyAuthInterceptor
) {

    @Bean
    fun spotifyApi(): SpotifyApi {
        val objectMapper = ObjectMapper()

        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                // Get the token from the SpotifyAuthInterceptor
                val token = authInterceptor.intercept(chain).body()?.string()
                    ?.let { responseBody ->
                        objectMapper.readTree(responseBody).get("access_token").asText()
                    }
                    ?: throw IllegalStateException("Unable to retrieve access token")

                println("SPOTIFY TOKEN $token")

                // Add the token to the request headers
                val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                chain.proceed(newRequest)
            }
            .build()

        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(Spotify.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(SpotifyApi::class.java)
    }

}