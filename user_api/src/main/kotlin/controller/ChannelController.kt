package com.moineaufactory.lingvasferoapi.controller

import com.moineaufactory.lingvasferoapi.dto.ChannelDto
import com.moineaufactory.lingvasferoapi.dto.ChannelSourceDto
import com.moineaufactory.lingvasferoapi.mapper.toChannelDto
import com.moineaufactory.lingvasferoapi.mapper.toChannelSourceDto
import com.moineaufactory.lingvasferoapi.service.ChannelService
import com.moineaufactory.lingvasferoapi.value.ChannelSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ClassPathResource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.io.IOException


@RestController
@RequestMapping("/api/channel")
class ChannelController @Autowired constructor(
    private val channelService: ChannelService
) {

    @GetMapping(value = ["/source_image"], params = ["id", "color"])
    @ResponseBody
    @Throws(
        IOException::class
    )
    fun getSourceImage(@RequestParam id: Int, @RequestParam color: Boolean): ResponseEntity<ByteArray> {
        val channel = ChannelSource.findById(id)
        val path = if (color) channel.imageColor else channel.imageBw

        val file = ClassPathResource(path)
        val imageBytes = file.contentAsByteArray

        val header = HttpHeaders()
        header.add("Content-Type", "image/svg+xml")

        return ResponseEntity.ok()
            .headers(header)
            .body(imageBytes)
    }

    @GetMapping("/id/{id}")
    fun getChannel(@PathVariable id: Long): ResponseEntity<ChannelDto?> {
        val channel = channelService.getById(id)?.toChannelDto()
        return ResponseEntity(channel, channel?.let { HttpStatus.OK } ?: HttpStatus.BAD_REQUEST)
    }

    @GetMapping("/find_by_content_creator_id")
    fun getChannelsByContentCreatorId(@RequestParam("content_creator_id") contentCreatorId: Long): ResponseEntity<List<ChannelDto>> {
        val channels = channelService.getByContentCreatorId(contentCreatorId).map { it.toChannelDto() }
        return ResponseEntity(channels, HttpStatus.OK)
    }

    @GetMapping("/sources")
    fun getChannelSources(): ResponseEntity<List<ChannelSourceDto>> {
        val sources = ChannelSource.entries.map { it.toChannelSourceDto() }
        return ResponseEntity(sources, HttpStatus.OK)
    }

}