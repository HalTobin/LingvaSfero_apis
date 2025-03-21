package com.moineaufactory.lingvasferoapi.api.repository

import com.moineaufactory.lingvasferoapi.api.dto.ChannelDetailsDto
import com.moineaufactory.lingvasferoapi.api.dto.ChannelPreviewDto
import com.moineaufactory.lingvasferoapi.dto.ContentDto
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.net.HttpURLConnection
import java.net.URL
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Service
class RssRepository @Autowired constructor(): SourceRepository {

    override suspend fun getContent(channelId: Long, link: String): List<ContentDto> {
        val rssDocument: Document = Jsoup.connect(link).get()
        val items: List<Element> = rssDocument.select("item")

        val pubDateFormatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH)

        return items.map { item ->
            val contentLink = item.selectFirst("link")?.text().orEmpty().removeTags()
            val content = item.selectFirst("content|encoded")?.text().orEmpty().removeTags()
            ContentDto(
                id = "${channelId}_$contentLink",
                channelId = channelId,
                title = item.selectFirst("title")?.text().orEmpty().removeTags(),
                content = content.ifEmpty { item.selectFirst("description")?.text().orEmpty().removeTags() },
                thumbnail = (item.selectFirst("media|content")
                    ?.attr("url")
                    ?: item.selectFirst("enclosure")?.attr("url"))
                    ?.removeTags(),
                link = contentLink,
                timestamp = item.selectFirst("pubDate")?.let { pubDate ->
                    try {
                        ZonedDateTime.parse(pubDate.text(), pubDateFormatter).toInstant().toEpochMilli()
                    } catch (e: Exception) {
                        System.currentTimeMillis() // Fallback in case of parse error
                    }
                } ?: System.currentTimeMillis()
            )
        }
    }

    override suspend fun searchChannel(search: String): List<ChannelPreviewDto> {
        return emptyList()
    }

    override suspend fun getChannelDetails(id: String): ChannelDetailsDto? {
        return null
    }

}


fun String.removeTags(): String {
    // Remove CDATA sections
    val cdataRegex = """<!\[CDATA\[(.*?)]]>""".toRegex(RegexOption.DOT_MATCHES_ALL)
    val withoutCdata = cdataRegex.replace(this, "$1")

    // Remove HTML tags
    return Jsoup.parse(withoutCdata).text()
}

fun String.isValidUrl(): Boolean {
    // Check if URL exists
    return try {
        val url = URL(this.removeTags())
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "HEAD"
        connection.connect()
        connection.responseCode == HttpURLConnection.HTTP_OK
    } catch (e: Exception) {
        false
    }
}

fun String.ifValidElseEmpty(): String {
    return if(this.isValidUrl()) this.removeTags()
    else ""
}