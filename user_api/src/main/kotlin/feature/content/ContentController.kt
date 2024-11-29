package com.moineaufactory.lingvasferoapi.feature.content

import com.moineaufactory.lingvasferoapi.feature.content.data.dto.ContentDto
import com.moineaufactory.lingvasferoapi.feature.content.data.service.ContentService
import com.moineaufactory.lingvasferoapi.service.ChannelService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
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
    fun getChannelContent(@RequestParam("channel_id") channelId: Long): ResponseEntity<List<ContentDto>> {
        channelService.getById(channelId)?.let { channel ->
            val content = contentService.getContentByChannel(channel)
            return ResponseEntity(content, HttpStatus.OK)
        }
        return ResponseEntity(emptyList(), HttpStatus.BAD_REQUEST)
    }

}