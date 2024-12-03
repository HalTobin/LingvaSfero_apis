package com.moineaufactory.lingvasferoapi.api.spotify

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

// Root response class for Spotify's podcast episodes API
data class SpotifyApiResponse(
    val href: String,
    val items: List<SpotifyEpisode>, // Represents the list of episodes
    val limit: Int,
    val next: String?,
    val offset: Int,
    val previous: String?,
    val total: Int
)

// Represents an individual episode in the response
data class SpotifyEpisode(
    val id: String,
    val name: String,
    val description: String,
    @SerializedName("duration_ms") val durationMs: Long,
    val explicit: Boolean,
    @SerializedName("external_urls") val externalUrls: ExternalUrls,
    val href: String,
    val images: List<SpotifyImage>,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("release_date_precision")val releaseDatePrecision: String,
    val type: String,
    val uri: String
) {

    fun getSmallThumbnail(): String? =
        images.minByOrNull { it.width }?.url

    fun getThumbnail(): String? =
        images.maxByOrNull { it.width }?.url

    fun releaseDateToEpochMilli(): Long {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = format.parse(releaseDate)
        return date.time
    }

}

// Represents external URLs, e.g., the Spotify URL for the episode
data class ExternalUrls(
    val spotify: String
)

// Represents image objects for the episode
data class SpotifyImage(
    val url: String,
    val height: Int,
    val width: Int
)