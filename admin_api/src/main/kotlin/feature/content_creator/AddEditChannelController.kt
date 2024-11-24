package com.moineaufactory.lingvasferoapi.feature.content_creator

import com.moineaufactory.lingvasferoapi.dto.ChannelDto
import com.moineaufactory.lingvasferoapi.feature.content_creator.data.AddEditChannelDto
import com.moineaufactory.lingvasferoapi.mapper.toChannelDto
import com.moineaufactory.lingvasferoapi.mapper.toChannelEntity
import com.moineaufactory.lingvasferoapi.service.ChannelService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin_api/channel")
class AddEditChannelController @Autowired constructor(
    private val channelService: ChannelService
) {

    @GetMapping("/add_edit")
    fun addEditChannel(
        @RequestBody addEditChannelDto: AddEditChannelDto
    ): ResponseEntity<ChannelDto?> {
        val channel = channelService.save(addEditChannelDto.toChannelEntity()).toChannelDto()
        return ResponseEntity(channel, HttpStatus.OK)
    }

}