package com.moineaufactory.lingvasferoapi.value

enum class ChannelSource(
    val id: Int,
    val title: String,
    val imageColor: String,
    val imageBw: String,
    val color: Int,
    val cacheValidity: Long
) {
    Rss(
        id = 1,
        title = "RSS",
        imageColor = "img/source/rss_color.svg",
        imageBw = "img/source/rss.svg",
        color = 0xFF6600,
        cacheValidity = 3600000),
    Youtube(
        id = 2,
        title = "Youtube",
        imageColor = "img/source/youtube_color.svg",
        imageBw = "img/source/youtube.svg",
        color = 0xFF0000,
        cacheValidity = 86400000),
    Spotify(
        id = 3,
        title = "Spotify",
        imageColor = "img/source/spotify_color.svg",
        imageBw = "img/source/spotify.svg",
        color = 0x1ED760,
        cacheValidity = 43200000);

    companion object {
        fun findById(id: Int) = ChannelSource.entries.find { it.id == id } ?: Rss
        fun findByIdOrNull(id: Int) = ChannelSource.entries.find { it.id == id }
    }

}