package com.moineaufactory.lingvasferoapi.feature.content

import com.moineaufactory.lingvasferoapi.dto.ContentDto
import com.moineaufactory.lingvasferoapi.feature.content.data.dto.UpdateContentDto
import com.moineaufactory.lingvasferoapi.feature.content.data.service.ContentService
import com.moineaufactory.lingvasferoapi.service.ChannelService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/content")
class ContentController @Autowired constructor(
    private val channelService: ChannelService,
    private val contentService: ContentService
) {

    @GetMapping("/get_channel_content")
    suspend fun getChannelContent(@RequestParam("channel_id") channelId: Long): ResponseEntity<List<ContentDto>> {
        return channelService.getById(channelId)?.let { channel ->
            val content = contentService.getContentByChannel(channel)
            ResponseEntity(content, HttpStatus.OK)
        } ?: ResponseEntity(emptyList(), HttpStatus.BAD_REQUEST)
    }

    @PostMapping("/update_content")
    suspend fun updateContent(
        @RequestBody update: UpdateContentDto
    ): ResponseEntity<List<ContentDto>> {
        // TODO - Implement the "timestamp" argument to lighten the returned json
        return coroutineScope {
            // Launch concurrent tasks for each channelId
            val deferredContents = update.channelsId.map { channelId ->
                async(Dispatchers.IO) { // Perform IO operations in parallel
                    channelService.getById(channelId)?.let { channel ->
                        contentService.getContentByChannel(channel)
                    } ?: emptyList()
                }
            }

            // Await all results and flatten them
            val content = deferredContents.awaitAll().flatten()

            ResponseEntity(content, HttpStatus.OK)
        }
    }

}