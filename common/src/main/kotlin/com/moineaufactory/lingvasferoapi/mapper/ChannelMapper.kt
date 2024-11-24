package com.moineaufactory.lingvasferoapi.mapper

import com.moineaufactory.lingvasferoapi.dto.ChannelDto
import com.moineaufactory.lingvasferoapi.entity.Channel
import com.moineaufactory.lingvasferoapi.value.ChannelSource

fun Channel.toChannelDto() = ChannelDto(
    id = this.id!!,
    contentCreatorId = this.contentCreatorId,
    title = this.title,
    source = ChannelSource.findById(this.sourceId).toChannelSourceDto(),
    sourceLink = this.sourceLink
)