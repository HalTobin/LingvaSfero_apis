package com.moineaufactory.lingvasferoapi.controller

import com.moineaufactory.lingvasferoapi.dto.ChannelSourceDto
import com.moineaufactory.lingvasferoapi.mapper.toChannelSourceDto
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
class ChannelController @Autowired constructor() {

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

    @GetMapping("/sources")
    fun getChannelSources(): ResponseEntity<List<ChannelSourceDto>> {
        val sources = ChannelSource.entries.map { it.toChannelSourceDto() }
        return ResponseEntity(sources, HttpStatus.OK)
    }

}