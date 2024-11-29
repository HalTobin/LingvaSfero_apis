package com.moineaufactory.lingvasferoapi.feature.content.data.api.youtube

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

data class YoutubeApiResponse(
    val items: List<YoutubeItem>
)

data class YoutubeItem(
    val snippet: YoutubeSnippet
)

data class YoutubeSnippet(
    val publishedAt: String,
    val channelId: String,
    val title: String,
    val description: String,
    val thumbnails: YoutubeThumbnails,
    val resourceId: YoutubeResourceId
) {
    // Convert publishedAt to epoch milliseconds
    fun publishedAtToEpochMilli(): Long {
        val formatter = DateTimeFormatter.ISO_DATE_TIME
        return ZonedDateTime.parse(publishedAt, formatter).toInstant().toEpochMilli()
    }
}

data class YoutubeThumbnails(
    val default: YoutubeThumbnail?,
    val medium: YoutubeThumbnail?,
    val high: YoutubeThumbnail?,
    val standard: YoutubeThumbnail?,
    val maxres: YoutubeThumbnail?
) {
    val thumbnail: String? =
        maxres?.url ?:
            standard?.url ?:
            high?.url ?:
            medium?.url ?:
            default?.url

    val thumbnailSmall: String? =
        standard?.url ?:
            medium?.url ?:
            high?.url
}

data class YoutubeThumbnail(
    val url: String
)

data class YoutubeResourceId(
    val videoId: String
)